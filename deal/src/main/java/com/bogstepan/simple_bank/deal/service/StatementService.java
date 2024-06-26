package com.bogstepan.simple_bank.deal.service;

import com.bogstepan.simple_bank.calculator_client.dto.LoanOfferDto;
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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StatementService {

    private final StatementRepository statementRepository;

    public Statement getById(String uuid) {
        var id = UUID.fromString(uuid);
        var statement = statementRepository.findById(id);
        if (statement.isEmpty()) {
            throw new RequestException(String.format("Statement with id %s not found", uuid));
        }
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

    public void calculatedCreditStatement(Statement statement, Credit credit) {
        statement.setCredit(credit);
        statement.setStatus(ApplicationStatus.CC_APPROVED);
        statement.getStatusHistory().addElement( new StatementStatusHistoryDto(
                ApplicationStatus.CC_APPROVED,
                LocalDateTime.now(),
                ChangeType.AUTOMATIC
        ));
        statementRepository.save(statement);
    }
}
