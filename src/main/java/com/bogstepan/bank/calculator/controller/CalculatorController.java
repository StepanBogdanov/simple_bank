package com.bogstepan.bank.calculator.controller;

import com.bogstepan.bank.calculator.dto.CreditDto;
import com.bogstepan.bank.calculator.dto.LoanOfferDto;
import com.bogstepan.bank.calculator.dto.LoanStatementRequestDto;
import com.bogstepan.bank.calculator.dto.ScoringDataDto;
import com.bogstepan.bank.calculator.service.CalculatorService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/calculator")
@RequiredArgsConstructor
public class CalculatorController {

    private final CalculatorService service;

    @PostMapping("/offers")
    public ResponseEntity<List<LoanOfferDto>> calculateOffers(@RequestBody LoanStatementRequestDto statementRequestDto) {
        var offers = service.calculateOffers(statementRequestDto);
        return ResponseEntity.ok(offers);
    }

    @PostMapping("/calc")
    public ResponseEntity<CreditDto> calculateCredit(@RequestBody ScoringDataDto scoringDataDto) {
        var credit = service.calculateCredit(scoringDataDto);
        return ResponseEntity.ok(credit);
    }
}
