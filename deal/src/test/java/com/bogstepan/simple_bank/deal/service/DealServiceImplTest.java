package com.bogstepan.simple_bank.deal.service;

import com.bogstepan.simple_bank.deal.exception.RequestException;
import com.bogstepan.simple_bank.deal.feign.CalculatorFeignClient;
import com.bogstepan.simple_bank.deal.mapping.ScoringDataMapper;
import com.bogstepan.simple_bank.deal.model.dto.*;
import com.bogstepan.simple_bank.deal.model.entity.Client;
import com.bogstepan.simple_bank.deal.model.entity.Credit;
import com.bogstepan.simple_bank.deal.model.entity.Statement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class DealServiceImplTest {

    @Mock
    ClientService clientService;
    @Mock
    StatementService statementService;
    @Mock
    CreditService creditService;
    @Mock
    CalculatorFeignClient calculatorFeignClient;
    @Mock
    ScoringDataMapper scoringDataMapper;

    @InjectMocks
    DealServiceImpl dealService;

    @Test
    public void whenCalculateOfferAndGiveOffers() {
        LoanStatementRequestDto request = new LoanStatementRequestDto();
        Client client = new Client();
        Statement statement = new Statement();
        statement.setStatementId(UUID.randomUUID());
        var offer = new LoanOfferDto();
        var response = ResponseEntity.ok(List.of(offer));
        Mockito.when(clientService.saveNewClient(request)).thenReturn(client);
        Mockito.when(statementService.preapprovalStatement(client)).thenReturn(statement);
        Mockito.when(calculatorFeignClient.calculateOffers(request)).thenReturn(response);
        var offers = dealService.calculateOffers(request);
        Mockito.verify(clientService, times(1)).saveNewClient(request);
        Mockito.verify(statementService, times(1)).preapprovalStatement(client);
        Mockito.verify(calculatorFeignClient, times(1)).calculateOffers(request);
        assertThat(offers.size()).isEqualTo(1);
        assertThat(offers.get(0)).isEqualTo(offer);
        assertThat(offers.get(0).getStatementId()).isEqualTo(statement.getStatementId());
    }

    @Test
    public void whenCalculateOffersAndGiveRequestException() {
        LoanStatementRequestDto request = new LoanStatementRequestDto();
        Client client = new Client();
        Statement statement = new Statement();
        statement.setStatementId(UUID.randomUUID());
        Mockito.when(clientService.saveNewClient(request)).thenReturn(client);
        Mockito.when(statementService.preapprovalStatement(client)).thenReturn(statement);
        Mockito.when(calculatorFeignClient.calculateOffers(request)).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        var exception = assertThrows(RequestException.class, () ->
                dealService.calculateOffers(request));
        Mockito.verify(clientService, times(1)).saveNewClient(request);
        Mockito.verify(statementService, times(1)).preapprovalStatement(client);
        Mockito.verify(calculatorFeignClient, times(1)).calculateOffers(request);
        assertThat(exception.getMessage()).isEqualTo(String.format("Failed to calculate offers for statement %s", statement.getStatementId()));
    }

    @Test
    public void selectOffer() {
        var offer = new LoanOfferDto();
        var statement = new Statement();
        Mockito.when(statementService.approvedStatement(offer)).thenReturn(statement);
        dealService.selectOffer(offer);
        Mockito.verify(statementService, times(1)).approvedStatement(offer);
    }

    @Test
    public void whenCalculateCreditAndGiveCredit() {
        var id = UUID.randomUUID().toString();
        var client = new Client();
        var statement = new Statement();
        statement.setClient(client);
        var request = new FinishRegistrationRequestDto();
        var scoringDataDto = new ScoringDataDto();
        var creditDto = new CreditDto();
        var credit = new Credit();
        Mockito.when(statementService.getById(id)).thenReturn(statement);
        Mockito.when(clientService.updateClient(client, request)).thenReturn(client);
        Mockito.when(scoringDataMapper.toScoringDataDto(statement, client)).thenReturn(scoringDataDto);
        Mockito.when(calculatorFeignClient.calculateCredit(scoringDataDto)).thenReturn(ResponseEntity.ok(creditDto));
        Mockito.when(creditService.saveCredit(creditDto)).thenReturn(credit);
        dealService.calculateCredit(request, id);
        Mockito.verify(statementService, times(1)).getById(id);
        Mockito.verify(clientService, times(1)).updateClient(client, request);
        Mockito.verify(scoringDataMapper, times(1)).toScoringDataDto(statement, client);
        Mockito.verify(calculatorFeignClient, times(1)).calculateCredit(scoringDataDto);
        Mockito.verify(creditService, times(1)).saveCredit(creditDto);
        Mockito.verify(statementService, times(1)).calculatedCreditStatement(statement, credit);

    }

    @Test
    public void whenCalculateCreditAndGiveRequestException() {
        var id = UUID.randomUUID().toString();
        var client = new Client();
        var statement = new Statement();
        statement.setStatementId(UUID.fromString(id));
        statement.setClient(client);
        var request = new FinishRegistrationRequestDto();
        var scoringDataDto = new ScoringDataDto();
        Mockito.when(statementService.getById(id)).thenReturn(statement);
        Mockito.when(clientService.updateClient(client, request)).thenReturn(client);
        Mockito.when(scoringDataMapper.toScoringDataDto(statement, client)).thenReturn(scoringDataDto);
        Mockito.when(calculatorFeignClient.calculateCredit(scoringDataDto)).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        var exception = assertThrows(RequestException.class, () -> dealService.calculateCredit(request, id));
        Mockito.verify(statementService, times(1)).getById(id);
        Mockito.verify(clientService, times(1)).updateClient(client, request);
        Mockito.verify(scoringDataMapper, times(1)).toScoringDataDto(statement, client);
        Mockito.verify(calculatorFeignClient, times(1)).calculateCredit(scoringDataDto);
        Mockito.verify(creditService, times(0)).saveCredit(any());
        Mockito.verify(statementService, times(0)).calculatedCreditStatement(any(), any());
        assertThat(exception.getMessage()).isEqualTo(String.format("Failed to calculate credit for statement %s", statement.getStatementId()));

    }
}