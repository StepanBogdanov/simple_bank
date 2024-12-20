package com.bogstepan.simple_bank.deal.service;

import com.bogstepan.simple_bank.clients.dto.LoanOfferDto;
import com.bogstepan.simple_bank.deal.exception.RequestException;
import com.bogstepan.simple_bank.deal.model.dto.StatementStatusHistoryDto;
import com.bogstepan.simple_bank.deal.model.entity.Client;
import com.bogstepan.simple_bank.deal.model.entity.Credit;
import com.bogstepan.simple_bank.deal.model.entity.Statement;
import com.bogstepan.simple_bank.deal.model.enums.ApplicationStatus;
import com.bogstepan.simple_bank.deal.model.enums.ChangeType;
import com.bogstepan.simple_bank.deal.model.json.StatusHistory;
import com.bogstepan.simple_bank.deal.repository.StatementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatementService {

    private final StatementRepository statementRepository;

    public Statement getById(String uuid) {
        var id = UUID.fromString(uuid);
        var statement = statementRepository.findById(id);
        if (statement.isEmpty()) {
            throw new RequestException(String.format("Statement with id %s not found", uuid));
        }
        log.info("Statement with id {} has been found", uuid);
        return statement.get();
    }

    public Statement preapprovalStatement(Client client) {
        var statusHistory = new StatusHistory();
        statusHistory.addElement(new StatementStatusHistoryDto(
                ApplicationStatus.PREAPPROVAL,
                LocalDateTime.now(),
                ChangeType.AUTOMATIC
        ));
        var statement = Statement.builder()
                .client(client)
                .status(ApplicationStatus.PREAPPROVAL)
                .statusHistory(statusHistory)
                .creationDate(LocalDateTime.now())
                .build();
        return statementRepository.save(statement);
    }

    public Statement approvedStatement(LoanOfferDto loanOfferDto) {
        var statement = getById(loanOfferDto.getStatementId().toString());
        statement.setStatus(ApplicationStatus.APPROVED);
        statement.setAppliedOffer(loanOfferDto);
        statement.getStatusHistory().addElement(new StatementStatusHistoryDto(
                ApplicationStatus.APPROVED,
                LocalDateTime.now(),
                ChangeType.AUTOMATIC
        ));
        return statementRepository.save(statement);
    }

    public void setCredit(Statement statement, Credit credit) {
        statement.setCredit(credit);
        statementRepository.save(statement);
    }

    public void updateStatementStatus(String statementID, ApplicationStatus status) {
        var statement = getById(statementID);
        statement.setStatus(status);
        statement.getStatusHistory().addElement(new StatementStatusHistoryDto(
                status,
                LocalDateTime.now(),
                ChangeType.AUTOMATIC
        ));
        if (status == ApplicationStatus.DOCUMENT_SIGNED) {
            statement.setSignDate(LocalDateTime.now());
        }
        statementRepository.save(statement);
        log.info("Statement's status wit id {} has been updated to {}", statementID, status.toString());
    }

    public void setSesCode(String statementId) {
        var statement = getById(statementId);
        var sesCode = String.valueOf((int) (Math.random()*900000) + 100000);
        statement.setSesCode(sesCode);
        statementRepository.save(statement);
    }

    public List<Statement> getStatements() {
        List<Statement> statements = new ArrayList<>();
        statementRepository.findAll().forEach(statements::add);
        return statements;
    }
}
