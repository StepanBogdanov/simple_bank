package com.bogstepan.simple_bank.statement.controller;

import com.bogstepan.simple_bank.statement.dto.LoanOfferDto;
import com.bogstepan.simple_bank.statement.dto.LoanStatementRequestDto;
import com.bogstepan.simple_bank.statement.exception.ExceptionData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Statement controller")
public interface StatementController {

    @Operation(summary = "Прескоринг и запрос на получение кредитных предложений от МС Deal", description = """
            По API приходит LoanStatementRequestDto
            На основе LoanStatementRequestDto происходит прескоринг.
            Отправляется POST-запрос на /deal/statement в МС deal через FeignClient.
            Ответ на API - список из 4х LoanOfferDto от "худшего" к "лучшему".
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Прескоринг прошел успешно, кредитные предложения получены"),
            @ApiResponse(responseCode = "400", description = "Ошибка получения данных из МС Deal", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionData.class)) })
    })
    @PostMapping("/statement")
    ResponseEntity<List<LoanOfferDto>> calculateOffers(@RequestBody LoanStatementRequestDto loanStatementRequestDto);

    @Operation(summary = "Выбор одного из кредитных предложений", description = """
            По API приходит LoanOfferDto
            Отправляется POST-запрос на /deal/offer/select в МС deal через FeignClient.
            """)
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Выбранное кредитное предложение отправлено в МС Deal"))
    @PostMapping("/satement/offer")
    void selectOffer(@RequestBody LoanOfferDto loanOfferDto);
}
