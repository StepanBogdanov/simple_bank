package com.bogstepan.simple_bank.deal.service;

import com.bogstepan.simple_bank.deal.model.dto.StatementStatusHistoryDto;
import com.bogstepan.simple_bank.deal.model.entity.Client;
import com.bogstepan.simple_bank.deal.model.entity.Statement;
import com.bogstepan.simple_bank.deal.model.enums.ApplicationStatus;
import com.bogstepan.simple_bank.deal.model.enums.ChangeType;
import com.bogstepan.simple_bank.deal.model.json.StatusHistory;
import com.bogstepan.simple_bank.deal.repository.StatementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StatementService {

    private final StatementRepository statementRepository;

    public void preapprovalStatement(Client client) {
        var statusHistory = new StatusHistory();
        statusHistory.addElement(new StatementStatusHistoryDto(
                ApplicationStatus.PREAPPROVAL,
                LocalDateTime.now(),
                ChangeType.AUTOMATIC
        ));
        statementRepository.save(Statement.builder()
                .client(client)
                .status(ApplicationStatus.PREAPPROVAL)
                .statusHistory(statusHistory)
                .build());
    }
}
