package com.bogstepan.simple_bank.clients.api;

import com.bogstepan.simple_bank.clients.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "МС Сделка")
public interface DealApi {

    @Operation(summary = "Расчет возможных условий кредита", description = """
            По API приходит LoanStatementRequestDto
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
    @PostMapping("/deal/statement")
    ResponseEntity<List<LoanOfferDto>> calculateOffers(@RequestBody LoanStatementRequestDto loanStatementRequestDto);

    @Operation(summary = "Выбор одного из предложений", description = """
            По API приходит LoanOfferDto
            Достаётся из БД заявка(Statement) по statementId из LoanOfferDto.
            В заявке обновляется статус, история статусов(List<StatementStatusHistoryDto>), принятое предложение LoanOfferDto устанавливается в поле appliedOffer.
            Заявка сохраняется.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявка успешно обновлена"),
            @ApiResponse(responseCode = "400", description = "Ошибка обновления заявки", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvalidRequestDataDto.class)) })
    })
    @PostMapping("/deal/offer/select")
    void selectOffer(@RequestBody LoanOfferDto loanOfferDto);

    @Operation(summary = "Завершение регистрации, полный расчет кредита", description = """
            По API приходит объект FinishRegistrationRequestDto и параметр statementId (String).
            Достаётся из БД заявка(Statement) по statementId.
            ScoringDataDto насыщается информацией из FinishRegistrationRequestDto и Client, который хранится в Statement
            Отправляется POST запрос на /calculator/calc МС Калькулятор с телом ScoringDataDto через FeignClient.
            На основе полученного из кредитного конвейера CreditDto создаётся сущность Credit и сохраняется в базу со статусом CALCULATED.
            В заявке обновляется статус, история статусов.
            Заявка сохраняется.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Кредит успешно расчитан"),
            @ApiResponse(responseCode = "400", description = "Ошибка расчета кредита", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvalidRequestDataDto.class)) })
    })
    @PostMapping("/deal/calculate/{statementId}")
    void calculateCredit(@RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto,
                         @Parameter(example = "2fbfeaef-ab99-49fa-951b-aa44bd58d309") @PathVariable("statementId") String statementId);

    @Operation(summary = "Запрос на отправку документов")
    @PostMapping("/deal/document/{statementId}/send")
    void sendDocs(@Parameter(example = "2fbfeaef-ab99-49fa-951b-aa44bd58d309") @PathVariable("statementId") String statementId);

    @Operation(summary = "Запрос на подписание документов")
    @PostMapping("/deal/document/{statementId}/sign")
    void signDocs(@Parameter(example = "2fbfeaef-ab99-49fa-951b-aa44bd58d309") @PathVariable("statementId") String statementId);

    @Operation(summary = "Подписание документов")
    @PostMapping("/deal/document/{statementId}/code")
    void codeDocs(@RequestParam("sesCode") String sesCode,
                  @Parameter(example = "2fbfeaef-ab99-49fa-951b-aa44bd58d309") @PathVariable("statementId") String statementId);

    @Operation(summary = "Получить заявку по id")
    @GetMapping("/deal/admin/statement/{statementId}")
    StatementDto getStatement(@Parameter(example = "2fbfeaef-ab99-49fa-951b-aa44bd58d309") @PathVariable("statementId") String statementId);

    @Operation(summary = "Обновить статус заявки")
    @PutMapping("/deal/admin/statement/{statementId}/status")
    void updateStatementStatus(@Parameter(example = "2fbfeaef-ab99-49fa-951b-aa44bd58d309") @PathVariable("statementId") String statementId);
}
