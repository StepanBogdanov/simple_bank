package com.bogstepan.simple_bank.statement.controller;

import com.bogstepan.simple_bank.calculator_client.api.StatementApi;
import com.bogstepan.simple_bank.calculator_client.dto.LoanOfferDto;
import com.bogstepan.simple_bank.calculator_client.dto.LoanStatementRequestDto;
import com.bogstepan.simple_bank.statement.service.StatementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class StatementController implements StatementApi {

    private final StatementService statementService;

    @Override
    public ResponseEntity<List<LoanOfferDto>> calculateOffers(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("An statement has been received for calculating loan offers {}", loanStatementRequestDto);
        var offers = statementService.calculateOffers(loanStatementRequestDto);
        log.info("Loan offers received: {}", offers);
        return ResponseEntity.ok(offers);
    }

    @Override
    public void selectOffer(LoanOfferDto loanOfferDto) {
        log.info("The selected loan offer has been received {}", loanOfferDto.getStatementId());
        statementService.selectOffer(loanOfferDto);
    }
}
