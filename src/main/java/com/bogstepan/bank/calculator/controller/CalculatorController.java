package com.bogstepan.bank.calculator.controller;

import com.bogstepan.bank.calculator.dto.CreditDto;
import com.bogstepan.bank.calculator.dto.LoanOfferDto;
import com.bogstepan.bank.calculator.dto.LoanStatementRequestDto;
import com.bogstepan.bank.calculator.dto.ScoringDataDto;
import com.bogstepan.bank.calculator.service.CalculatorService;
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
public class CalculatorController {

    private final CalculatorService service;

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
