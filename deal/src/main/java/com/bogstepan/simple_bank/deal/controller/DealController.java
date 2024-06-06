package com.bogstepan.simple_bank.deal.controller;

import com.bogstepan.simple_bank.deal.model.dto.FinishRegistrationRequestDto;
import com.bogstepan.simple_bank.deal.model.dto.LoanOfferDto;
import com.bogstepan.simple_bank.deal.model.dto.LoanStatementRequestDto;
import com.bogstepan.simple_bank.deal.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deal")
@RequiredArgsConstructor
public class DealController {

    private final DealService dealService;

    @PostMapping("/statement")
    public ResponseEntity<List<LoanOfferDto>> calculateOffers(@RequestBody LoanStatementRequestDto loanStatementRequestDto){
        var offers = dealService.calculateOffers(loanStatementRequestDto);
        return ResponseEntity.ok(offers);
    }

    @PostMapping("/offer/select")
    public void selectOffer(@RequestBody LoanOfferDto loanOfferDto) {
        dealService.selectOffer(loanOfferDto);
    }

    @PostMapping("/calculate/{statementId}")
    public void calculateCredit(@RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto,
                                @PathVariable String statementId) {

    }
}
