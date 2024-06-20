package com.bogstepan.simple_bank.statement.controller;

import com.bogstepan.simple_bank.statement.dto.LoanOfferDto;
import com.bogstepan.simple_bank.statement.dto.LoanStatementRequestDto;
import com.bogstepan.simple_bank.statement.service.StatementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class StatementControllerImpl implements StatementController {

    private final StatementService statementService;

    @Override
    public ResponseEntity<List<LoanOfferDto>> calculateOffers(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("Поступила заявка на расчет кредитных предложений: {}", loanStatementRequestDto);
        var offers = statementService.calculateOffers(loanStatementRequestDto);
        log.info("Кредитные предложения получены: {}", offers);
        return ResponseEntity.ok(offers);
    }

    @Override
    public void selectOffer(LoanOfferDto loanOfferDto) {
        log.info("Поступило выбранное кредитное предложение по заявке {}", loanOfferDto.getStatementId());
        statementService.selectOffer(loanOfferDto);
    }
}
