package com.bogstepan.bank.calculator.controller;

import com.bogstepan.bank.calculator.dto.CreditDto;
import com.bogstepan.bank.calculator.dto.LoanOfferDto;
import com.bogstepan.bank.calculator.dto.LoanStatementRequestDto;
import com.bogstepan.bank.calculator.dto.ScoringDataDto;
import com.bogstepan.bank.calculator.service.CalculatorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculatorServiceImpl implements CalculatorService {

    @Override
    public List<LoanOfferDto> calculateOffers(LoanStatementRequestDto loanStatementRequestDto) {
        return List.of();
    }

    @Override
    public CreditDto calculateCredit(ScoringDataDto scoringDataDto) {
        return null;
    }
}
