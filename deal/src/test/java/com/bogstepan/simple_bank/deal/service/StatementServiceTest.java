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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class StatementServiceTest {

    @Mock
    StatementRepository statementRepository;

    @InjectMocks
    StatementService statementService;

    @Test
    public void whenSuccessfullyGetByIdThenGiveStatement() {
        var id = UUID.randomUUID();
        Mockito.when(statementRepository.findById(id)).thenReturn(Optional.of(new Statement()));
        statementService.getById(id.toString());
        Mockito.verify(statementRepository, times(1)).findById(id);
    }

    @Test
    public void whenUnsuccessfullyGetByIdThenGiveRequestException() {
        var id = UUID.randomUUID();
        Mockito.when(statementRepository.findById(id)).thenReturn(Optional.empty());
        var exception = assertThrows(RequestException.class, () ->
                statementService.getById(id.toString()));
        Mockito.verify(statementRepository, times(1)).findById(id);
        assertThat(exception.getMessage()).isEqualTo(String.format("Statement with id %s not found", id));
    }

    @Test
    public void preapprovalStatement() {
        var client = new Client();
        Mockito.when(statementRepository.save(any())).then((Answer<Statement>) invocation -> {
            Object[] args = invocation.getArguments();
            return (Statement) args[0];
        });
        var statement = statementService.preapprovalStatement(client);
        Mockito.verify(statementRepository, times(1)).save(statement);
        assertThat(statement.getClient()).isEqualTo(client);
        assertThat(statement.getStatus()).isEqualTo(ApplicationStatus.PREAPPROVAL);
        assertThat(statement.getStatusHistory().getStatusHistory().size()).isEqualTo(1);
    }

    @Test
    public void approvedStatement() {
        var id = UUID.randomUUID();
        var offer = new LoanOfferDto(id, null, null, null, null, null, null, null);
        var statement = new Statement(
                null, null, null, ApplicationStatus.PREAPPROVAL, null, null, null, null,
                new StatusHistory()
        );
        statement.getStatusHistory().addElement(new StatementStatusHistoryDto(
                ApplicationStatus.PREAPPROVAL,
                LocalDateTime.now(),
                ChangeType.AUTOMATIC
        ));
        Mockito.when(statementRepository.save(any())).then((Answer<Statement>) invocation -> {
            Object[] args = invocation.getArguments();
            return (Statement) args[0];
        });
        Mockito.when(statementRepository.findById(id)).thenReturn(Optional.of(statement));
        statementService.approvedStatement(offer);
        Mockito.verify(statementRepository, times(1)).findById(id);
        Mockito.verify(statementRepository, times(1)).save(statement);
        assertThat(statement.getAppliedOffer()).isEqualTo(offer);
        assertThat(statement.getStatus()).isEqualTo(ApplicationStatus.APPROVED);
        assertThat(statement.getStatusHistory().getStatusHistory().size()).isEqualTo(2);
    }

    @Test
    public void calculatedCreditStatement() {
        var credit = new Credit();
        credit.setCreditId(UUID.randomUUID());
        var statement = new Statement(
                null, null, null, ApplicationStatus.APPROVED, null, null, null, null,
                new StatusHistory()
        );
        statement.getStatusHistory().addElement(new StatementStatusHistoryDto(
                ApplicationStatus.PREAPPROVAL,
                LocalDateTime.now(),
                ChangeType.AUTOMATIC
        ));
        statement.getStatusHistory().addElement(new StatementStatusHistoryDto(
                ApplicationStatus.APPROVED,
                LocalDateTime.now(),
                ChangeType.AUTOMATIC
        ));
        Mockito.when(statementRepository.save(any())).then((Answer<Statement>) invocation -> {
            Object[] args = invocation.getArguments();
            return (Statement) args[0];
        });
        statementService.calculatedCreditStatement(statement, credit);
        Mockito.verify(statementRepository, times(1)).save(statement);
        assertThat(statement.getCredit()).isEqualTo(credit);
        assertThat(statement.getStatus()).isEqualTo(ApplicationStatus.CC_APPROVED);
        assertThat(statement.getStatusHistory().getStatusHistory().size()).isEqualTo(3);
    }

}