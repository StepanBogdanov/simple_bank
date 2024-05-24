package com.bogstepan.bank.calculator.controller;

import com.bogstepan.bank.calculator.dto.CreditDto;
import com.bogstepan.bank.calculator.dto.LoanOfferDto;
import com.bogstepan.bank.calculator.dto.LoanStatementRequestDto;
import com.bogstepan.bank.calculator.dto.ScoringDataDto;
import com.bogstepan.bank.calculator.service.CalculatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/calculator")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Calculator controller")
public class CalculatorController {

    private final CalculatorService service;

    @Operation(summary = "Calculate loan offers", description = "Based on LoanStatementRequestDto, prescoring occurs, " +
            "4 loan offers LoanOfferDto are calculated based on all possible combinations of the isInsuranceEnabled " +
            "and isSalaryClient Boolean fields (false-false, false-true, true-false, true-true).\n")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully calculated"),
            @ApiResponse(responseCode = "400", description = "Unsuccessfully validated", content = {
                    @Content(schema = @Schema(hidden = true))
            })
    })

    @PostMapping("/offers")
    public ResponseEntity<List<LoanOfferDto>> calculateOffers(@RequestBody LoanStatementRequestDto statementRequestDto) {
        log.info("Loan request statement: {}", statementRequestDto);
        var offers = service.calculateOffers(statementRequestDto);
        if (!offers.isEmpty()) {
            log.info("Loan offers: {}", offers);
            return ResponseEntity.ok(offers);
        }
        return new ResponseEntity<>(offers, HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Calculate credit", description = "ScoringDataDto comes via API.\n" +
            "The data is scored, the final rate (rate), the total cost of the loan (psk), the amount of the monthly " +
            "payment (monthlyPayment), and the monthly payment schedule (List<PaymentScheduleElementDto>) are calculated." +
            "The response to the API is CreditDto, rich in all calculated parameters.\n")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully calculated"),
            @ApiResponse(responseCode = "400", description = "Credit denied", content = {
                    @Content(schema = @Schema(hidden = true))
            })
    })
    @PostMapping("/calc")
    public ResponseEntity<CreditDto> calculateCredit(@RequestBody ScoringDataDto scoringDataDto) {
        log.info("Request scoring data: {}", scoringDataDto);
        var credit = service.calculateCredit(scoringDataDto);
        if (credit.isPresent()) {
            log.info("Credit calculated: {}", credit);
            return ResponseEntity.ok(credit.get());
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
