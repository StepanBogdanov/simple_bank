package com.bogstepan.simple_bank.statement.service;

import com.bogstepan.simple_bank.statement.dto.LoanOfferDto;
import com.bogstepan.simple_bank.statement.dto.LoanStatementRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatementServiceImpl implements StatementService {

    @Override
    public List<LoanOfferDto> calculateOffers(LoanStatementRequestDto loanStatementRequestDto) {
        return List.of();
    }

    @Override
    public void selectOffer(LoanOfferDto loanOfferDto) {

    }
}
