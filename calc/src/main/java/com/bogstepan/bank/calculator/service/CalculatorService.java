package com.bogstepan.bank.calculator.service;

import com.bogstepan.bank.calculator.dto.CreditDto;
import com.bogstepan.bank.calculator.dto.LoanOfferDto;
import com.bogstepan.bank.calculator.dto.LoanStatementRequestDto;
import com.bogstepan.bank.calculator.dto.ScoringDataDto;

import java.util.List;
import java.util.Optional;

public interface CalculatorService {

     List<LoanOfferDto> calculateOffers(LoanStatementRequestDto loanStatementRequestDto);
     Optional<CreditDto> calculateCredit(ScoringDataDto scoringDataDto);
}
