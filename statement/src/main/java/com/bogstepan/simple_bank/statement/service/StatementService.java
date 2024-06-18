package com.bogstepan.simple_bank.statement.service;

import com.bogstepan.simple_bank.statement.dto.LoanOfferDto;
import com.bogstepan.simple_bank.statement.dto.LoanStatementRequestDto;

import java.util.List;

public interface StatementService {

    List<LoanOfferDto> calculateOffers(LoanStatementRequestDto loanStatementRequestDto);

    void selectOffer(LoanOfferDto loanOfferDto);
}
