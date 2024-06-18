package com.bogstepan.simple_bank.deal.service;

import com.bogstepan.simple_bank.deal.model.dto.FinishRegistrationRequestDto;
import com.bogstepan.simple_bank.deal.model.dto.LoanOfferDto;
import com.bogstepan.simple_bank.deal.model.dto.LoanStatementRequestDto;

import java.util.List;

public interface DealService {

    List<LoanOfferDto> calculateOffers(LoanStatementRequestDto loanStatementRequestDto);

    void selectOffer(LoanOfferDto loanOfferDto);

    void calculateCredit(FinishRegistrationRequestDto finishRegistrationRequestDto, String statementId);
}
