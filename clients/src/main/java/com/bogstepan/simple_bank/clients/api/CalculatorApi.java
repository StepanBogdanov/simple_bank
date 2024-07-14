package com.bogstepan.simple_bank.clients.api;

import com.bogstepan.simple_bank.clients.dto.*;
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

@Tag(name = "МС Калькулятор")
public interface CalculatorApi {

    @Operation(summary = "Расчет кредитных предложений", description = """
            По API приходит LoanStatementRequestDto.
            На основании LoanStatementRequestDto создаётся 4 кредитных предложения LoanOfferDto
            на основании всех возможных комбинаций булевских полей isInsuranceEnabled и isSalaryClient
            (false-false, false-true, true-false, true-true)
            Ответ на API - список из 4х LoanOfferDto от "худшего" к "лучшему" (чем меньше итоговая ставка, тем лучше).
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Кредитные предложения успешно расчитаны"),
            @ApiResponse(responseCode = "400", description = "Ошибка расчета кредитных предложений", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvalidRequestDataDto.class)) })
    })
    @PostMapping("/calculator/offers")
    ResponseEntity<List<LoanOfferDto>> calculateOffers(@RequestBody LoanStatementRequestDto statementRequestDto);

    @Operation(summary = "Полный расчет кредита", description = """
            По API приходит ScoringDataDto.
            Происходит скоринг данных, высчитывание итоговой ставки(rate), полной стоимости кредита(psk),
            размер ежемесячного платежа(monthlyPayment), график ежемесячных платежей (List<PaymentScheduleElementDto>).
            Ответ на API - CreditDto, насыщенный всеми рассчитанными параметрами.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Кредит успешно расчитан"),
            @ApiResponse(responseCode = "400", description = "Ошибка расчета кредита", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvalidRequestDataDto.class)) })
    })
    @PostMapping("/calculator/calc")
    ResponseEntity<CreditDto> calculateCredit(@RequestBody ScoringDataDto scoringDataDto);
}
