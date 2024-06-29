package com.bogstepan.simple_bank.deal.controller;

import com.bogstepan.simple_bank.calculator_client.api.DealApi;
import com.bogstepan.simple_bank.calculator_client.dto.FinishRegistrationRequestDto;
import com.bogstepan.simple_bank.calculator_client.dto.LoanOfferDto;
import com.bogstepan.simple_bank.calculator_client.dto.LoanStatementRequestDto;
import com.bogstepan.simple_bank.deal.service.DealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DealController implements DealApi {

    private final DealService dealService;
    @Override
    public ResponseEntity<List<LoanOfferDto>> calculateOffers(LoanStatementRequestDto loanStatementRequestDto){
        log.info("Loan statement request: {}", loanStatementRequestDto );
        var offers = dealService.calculateOffers(loanStatementRequestDto);
        log.info("Loan offers calculated: {}", offers);
        return ResponseEntity.ok(offers);
    }

    @Override
    public void selectOffer(LoanOfferDto loanOfferDto) {
        log.info("Selected offer for statement with Id {}: {}", loanOfferDto.getStatementId(), loanOfferDto);
        dealService.selectOffer(loanOfferDto);
    }

    @Override
    public void calculateCredit(FinishRegistrationRequestDto finishRegistrationRequestDto, String statementId) {
        log.info("Finish registration request for statement with Id {}: {}", statementId, finishRegistrationRequestDto );
        dealService.calculateCredit(finishRegistrationRequestDto, statementId);
    }
}
