package com.bogstepan.gateway.controller;

import com.bogstepan.gateway.feign.DealFeignClient;
import com.bogstepan.gateway.feign.StatementFeignClient;
import com.bogstepan.simple_bank.clients.dto.FinishRegistrationRequestDto;
import com.bogstepan.simple_bank.clients.dto.InvalidRequestDataDto;
import com.bogstepan.simple_bank.clients.dto.LoanOfferDto;
import com.bogstepan.simple_bank.clients.dto.LoanStatementRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Gateway API")
public class GatewayController {

    private final DealFeignClient dealFeignClient;
    private final StatementFeignClient statementFeignClient;

    @Operation(summary = "Расчет возможных условий кредита", description = """
            По API приходит LoanStatementRequestDto
            Через FeignClient отправляется POST запрос на /statement МС Заявка.
            На основе LoanStatementRequestDto происходит прескоринг данных.
            Через FeignClient отправляется POST запрос на /deal/statement МС Сделка.
            На основе LoanStatementRequestDto создаётся сущность Client и сохраняется в БД.
            Создаётся Statement со связью на только что созданный Client и сохраняется в БД.
            Отправляется POST запрос на /calculator/offers МС Калькулятор через FeignClient
            Каждому элементу из списка List<LoanOfferDto> присваивается id созданной заявки (Statement)
            Ответ на API - список из 4х LoanOfferDto от "худшего" к "лучшему".
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Расчет выполнен успешно"),
            @ApiResponse(responseCode = "400", description = "Ошибка выполнения расчета", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvalidRequestDataDto.class)) })
    })
    @PostMapping("/statement")
    ResponseEntity<List<LoanOfferDto>> calculateOffers(@RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        log.info("An statement has been received to Gateway for calculating loan offers {}", loanStatementRequestDto);
        return statementFeignClient.calculateOffers(loanStatementRequestDto);
    }

    @Operation(summary = "Выбор одного из предложений", description = """
            По API приходит LoanOfferDto
            Через FeignClient отправляется POST запрос на /statement/offer МС Заявка.
            Через FeignClient отправляется POST запрос на /deal/offer/select МС Сделка.
            Из БД достается заявка(Statement) по statementId из LoanOfferDto.
            Обновляется статус заявки (APPROVED), принятое предложение LoanOfferDto устанавливается в поле appliedOffer.
            Заявка сохраняется.
            Kafka отправляет ссобщение в топик finish_registration.
            МС Досье читает сообщение из этого топика, формирует и отправляет клиенту письмо с предложением завершить регистрацию.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявка успешно обновлена, письмо клиенту отправлено"),
            @ApiResponse(responseCode = "400", description = "Ошибка обновления заявки или отправки письма", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvalidRequestDataDto.class)) })
    })
    @PostMapping("/statement/select")
    void selectOffer(@RequestBody LoanOfferDto loanOfferDto) {
        log.info("The selected loan offer has been received {} to Gateway", loanOfferDto.getStatementId());
        dealFeignClient.selectOffer(loanOfferDto);
    }

    @Operation(summary = "Завершение регистрации, полный расчет кредита", description = """
            По API приходит объект FinishRegistrationRequestDto и параметр statementId (String).
            Через FeignClient отправляется POST запрос на /deal/calculate/{statementId} МС Сделка.
            Из БД достается заявка(Statement) по statementId.
            ScoringDataDto насыщается информацией из FinishRegistrationRequestDto и Client, который хранится в Statement
            Отправляется POST запрос на /calculator/calc МС Калькулятор с телом ScoringDataDto через FeignClient.
            На основе полученного из кредитного конвейера CreditDto создаётся сущность Credit и сохраняется в базу со статусом CALCULATED.
            Обновляется статус заявки (CC_APPROVED).
            Заявка сохраняется.
            Kafka отправляет ссобщение в топик create_documents.
            МС Досье читает сообщение из этого топика, формирует и отправляет клиенту письмо с уведомлением об одобрении кредита и
            предожением получения кредитных документов.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Кредит успешно расчитан, письмо клиенту отправлено"),
            @ApiResponse(responseCode = "400", description = "Ошибка расчета кредита или отправки письма", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvalidRequestDataDto.class)) })
    })
    @PostMapping("/statement/registration/{statementId}")
    void calculateCredit(@RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto,
                         @Parameter(example = "2fbfeaef-ab99-49fa-951b-aa44bd58d309") @PathVariable("statementId") String statementId) {
        log.info("Finish registration request for statement with Id {} has been received to Gateway: {}", statementId, finishRegistrationRequestDto );
        dealFeignClient.calculateCredit(finishRegistrationRequestDto, statementId);
    }

    @Operation(summary = "Запрос на отправку документов", description = """
            По API приходит параметр statementId (String).
            Через FeignClient отправляется POST запрос на /deal/document/{statementId}/send МС Заявка.
            Обновляется статус заявки(PREPARE_DOCUMENTS).
            Kafka отправляет ссобщение в топик create_documents.
            МС Досье читает сообщение из этого топика, формирует кредитные документы.
            Через FeignClient МС Досье отправляется PUT запрос на /deal/admin/statement/{statementId}/status МС Сделка.
            Обновляется статус заявки(DOCUMENTS_CREATED).
            МС Досье отправляет клиенту письмо со сформированными кредитными документами и ссылками на согласие или отказ от кредита.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявка успешно обновлена, письмо клиенту отправлено"),
            @ApiResponse(responseCode = "400", description = "Ошибка обновления заявки или отправки письма", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvalidRequestDataDto.class)) })
    })
    @PostMapping("/document/{statementId}")
    void sendDocs(@Parameter(example = "2fbfeaef-ab99-49fa-951b-aa44bd58d309") @PathVariable("statementId") String statementId) {
        log.info("Create documents request for statement {} has been received to Gateway", statementId);
        dealFeignClient.sendDocs(statementId);
    }

    @Operation(summary = "Запрос на подписание документов", description = """
            По API приходит параметр statementId(String).
            Через FeignClient отправляется POST запрос на /deal/document/{statementId}/sign МС Заявка.
            В заявке генериркется случайное шестизначное число и установлиевается в поле SesCode.
            Kafka отправляет ссобщение в топик send_ses.
            МС Досье читает сообщение из этого топика,отправляет клиенту письмо со сгенерированным кодом и ссылкой на ввод кода.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявка успешно обновлена, письмо клиенту отправлено"),
            @ApiResponse(responseCode = "400", description = "Ошибка обновления заявки или отправки письма", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvalidRequestDataDto.class)) })
    })
    @PostMapping("/document/{statementId}/sign")
    void signDocs(@Parameter(example = "2fbfeaef-ab99-49fa-951b-aa44bd58d309") @PathVariable("statementId") String statementId) {
        log.info("Sign documents request for statement {} has been received to Gateway", statementId);
        dealFeignClient.signDocs(statementId);
    }

    @Operation(summary = "Подписание документов", description = """
            По API приходит параметр statementId(String) и sesCode(String).
            Через FeignClient отправляется POST запрос на /deal/document/{statementId}/code МС Заявка.
            Полученный sesCode сравнивается с кодом, хранящимся в заявке. При успешной проверкке обновляется статус заявки(DOCUMENTS_SIGNED).
            Обновляется статус заявки(CREDIT_ISSUED).
            Kafka отправляет ссобщение в топик credit_issued.
            МС Досье читает сообщение из этого топика,отправляет клиенту письмо с уведомлением о получении кредита.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявка успешно обновлена, письмо клиенту отправлено"),
            @ApiResponse(responseCode = "400", description = "Ошибка обновления заявки или отправки письма", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvalidRequestDataDto.class)) })
    })
    @PostMapping("/document/{statementId}/sign/code")
    void codeDocs(@Parameter(example = "123456") @RequestParam("sesCode") String sesCode,
                  @Parameter(example = "2fbfeaef-ab99-49fa-951b-aa44bd58d309") @PathVariable("statementId") String statementId) {
        log.info("Verify ses code request for statement {} has been received to Gateway", statementId);
        dealFeignClient.codeDocs(sesCode, statementId);
    }

}
