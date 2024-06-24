package com.bogstepan.simple_bank.calculator_client;

import com.bogstepan.simple_bank.calculator_client.dto.CreditDto;
import com.bogstepan.simple_bank.calculator_client.dto.LoanOfferDto;
import com.bogstepan.simple_bank.calculator_client.dto.LoanStatementRequestDto;
import com.bogstepan.simple_bank.calculator_client.dto.ScoringDataDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/calculator")
public interface CalculatorApi {

    @PostMapping("/offers")
    ResponseEntity<List<LoanOfferDto>> calculateOffers(@RequestBody LoanStatementRequestDto statementRequestDto);

    @PostMapping("/calc")
    ResponseEntity<CreditDto> calculateCredit(@RequestBody ScoringDataDto scoringDataDto);
}
