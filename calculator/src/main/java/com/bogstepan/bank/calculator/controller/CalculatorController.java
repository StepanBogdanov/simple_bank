package com.bogstepan.bank.calculator.controller;

import com.bogstepan.bank.calculator.exception.InvalidRequestException;
import com.bogstepan.bank.calculator.service.CalculatorService;
import com.bogstepan.simple_bank.calculator_client.api.CalculatorApi;
import com.bogstepan.simple_bank.calculator_client.dto.CreditDto;
import com.bogstepan.simple_bank.calculator_client.dto.LoanOfferDto;
import com.bogstepan.simple_bank.calculator_client.dto.LoanStatementRequestDto;
import com.bogstepan.simple_bank.calculator_client.dto.ScoringDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CalculatorController implements CalculatorApi {

    private final CalculatorService service;

    @Override
    public ResponseEntity<List<LoanOfferDto>> calculateOffers(@RequestBody LoanStatementRequestDto statementRequestDto) {
        log.info("Loan request statement: {}", statementRequestDto);
        var offers = service.calculateOffers(statementRequestDto);
        if (offers.isEmpty()) {
            log.warn("Offers calculation error");
            throw new InvalidRequestException("Offers calculation error");
        }
        log.info("Loan offers: {}", offers);
        return ResponseEntity.ok(offers);
    }

    @Override
    public ResponseEntity<CreditDto> calculateCredit(@RequestBody ScoringDataDto scoringDataDto) {
        log.info("Request scoring data: {}", scoringDataDto);
        var credit = service.calculateCredit(scoringDataDto);
        if (credit.isEmpty()) {
            log.warn("Credit calculation error");
            throw new InvalidRequestException("Credit calculation error");
        }
        log.info("Credit calculated: {}", credit);
        return ResponseEntity.ok(credit.get());
    }
}
