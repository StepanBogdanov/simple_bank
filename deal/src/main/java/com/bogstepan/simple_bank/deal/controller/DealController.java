package com.bogstepan.simple_bank.deal.controller;

import com.bogstepan.simple_bank.clients.api.DealApi;
import com.bogstepan.simple_bank.clients.dto.FinishRegistrationRequestDto;
import com.bogstepan.simple_bank.clients.dto.LoanOfferDto;
import com.bogstepan.simple_bank.clients.dto.LoanStatementRequestDto;
import com.bogstepan.simple_bank.clients.dto.StatementDto;
import com.bogstepan.simple_bank.deal.service.DealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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

    @Override
    public void sendDocs(String statementId) {
        log.info("Create documents request for statement {} was received", statementId);
        dealService.prepareDocuments(statementId);
    }

    @Override
    public void signDocs(String statementId) {
        log.info("Sign documents request for statement {} was received", statementId);
        dealService.setStatementSesCode(statementId);
    }

    @Override
    public void codeDocs(String sesCode, String statementId) {
        log.info("Verify ses code request for statement {} was received", statementId);
        dealService.verifyingSesCode(sesCode, statementId);
    }

    @Override
    public StatementDto getStatement(String statementId) {
        log.info("Get statement request for statement {} was received", statementId);
        return dealService.getStatement(statementId);
    }

    @Override
    public void updateStatementStatus(String statementId) {
        log.info("Update statement request for statement {} was received", statementId);
        dealService.updateStatementStatusDocumentsCreated(statementId);
    }

    @Override
    public List<StatementDto> getStatements() {
        log.info("Get all statements request was received");
        return dealService.getStatements();
    }
}
