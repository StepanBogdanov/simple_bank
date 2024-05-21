package com.bogstepan.bank.calculator.service;

import com.bogstepan.bank.calculator.dto.CreditDto;
import com.bogstepan.bank.calculator.dto.LoanOfferDto;
import com.bogstepan.bank.calculator.dto.LoanStatementRequestDto;
import com.bogstepan.bank.calculator.dto.ScoringDataDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CalculatorService {

    public List<LoanOfferDto> calculateOffers(LoanStatementRequestDto loanStatementRequestDto);
    public CreditDto calculateCredit(ScoringDataDto scoringDataDto);
}
