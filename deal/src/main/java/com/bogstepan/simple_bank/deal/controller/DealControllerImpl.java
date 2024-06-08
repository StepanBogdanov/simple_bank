package com.bogstepan.simple_bank.deal.controller;

import com.bogstepan.simple_bank.deal.model.dto.FinishRegistrationRequestDto;
import com.bogstepan.simple_bank.deal.model.dto.LoanOfferDto;
import com.bogstepan.simple_bank.deal.model.dto.LoanStatementRequestDto;
import com.bogstepan.simple_bank.deal.service.DealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deal")
@RequiredArgsConstructor
@Slf4j
public class DealControllerImpl implements DealController {

    private final DealService dealService;

    @Override
    @PostMapping("/statement")
    public ResponseEntity<List<LoanOfferDto>> calculateOffers(@RequestBody LoanStatementRequestDto loanStatementRequestDto){
        log.info("Loan statement request: {}", loanStatementRequestDto );
        var offers = dealService.calculateOffers(loanStatementRequestDto);
        log.info("Loan offers calculated: {}", offers);
        return ResponseEntity.ok(offers);
    }

    @Override
    @PostMapping("/offer/select")
    public void selectOffer(@RequestBody LoanOfferDto loanOfferDto) {
        log.info("Selected offer for statement with Id {}: {}", loanOfferDto.getStatementId(), loanOfferDto);
        dealService.selectOffer(loanOfferDto);
    }

    @Override
    @PostMapping("/calculate/{statementId}")
    public void calculateCredit(@RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto,
                                @PathVariable String statementId) {
        log.info("Finish registration request for statement with Id {}: {}", statementId, finishRegistrationRequestDto );
        dealService.calculateCredit(finishRegistrationRequestDto, statementId);
    }
}
