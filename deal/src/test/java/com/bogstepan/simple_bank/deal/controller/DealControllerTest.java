package com.bogstepan.simple_bank.deal.controller;

import com.bogstepan.simple_bank.clients.dto.FinishRegistrationRequestDto;
import com.bogstepan.simple_bank.clients.dto.LoanOfferDto;
import com.bogstepan.simple_bank.clients.dto.LoanStatementRequestDto;
import com.bogstepan.simple_bank.clients.dto.StatementDto;
import com.bogstepan.simple_bank.deal.service.DealService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class DealControllerTest {

    @Mock
    DealService dealService;

    @InjectMocks
    DealController controller;

    @Test
    public void whenCalculateOffersThenGiveOkResponseEntity() {
        var offer = new LoanOfferDto();
        Mockito.when(dealService.calculateOffers(any())).thenReturn(List.of(offer));
        var response = controller.calculateOffers(new LoanStatementRequestDto());
        Mockito.verify(dealService, times(1)).calculateOffers(any());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(List.of(offer));
    }

    @Test
    public void whenControllerSelectOfferThenInvokeDealServiceSelectOffer() {
        var offer = new LoanOfferDto();
        controller.selectOffer(offer);
        Mockito.verify(dealService, times(1)).selectOffer(offer);
    }

    @Test
    public void whenControllerCalculateCreditThenInvokeDealServiceCalculateCredit() {
        var request = new FinishRegistrationRequestDto();
        var id = "123456";
        controller.calculateCredit(request, id);
        Mockito.verify(dealService, times(1)).calculateCredit(request, id);
    }

    @Test
    public void whenControllerSendDocsThenInvokeDealServicePrepareDocuments() {
        String id = "123";
        controller.sendDocs(id);
        Mockito.verify(dealService, times(1)).prepareDocuments(id);
    }

    @Test
    public void whenControllerSignDocsThenInvokeDealServiceSetStatementSesCode() {
        String id = "123";
        controller.signDocs(id);
        Mockito.verify(dealService, times(1)).setStatementSesCode(id);
    }

    @Test
    public void whenControllerCodeDocsThenInvokeDealServiceVerifyingSesCode() {
        String id = "123";
        String code = "123456";
        controller.codeDocs(code, id);
        Mockito.verify(dealService, times(1)).verifyingSesCode(code, id);
    }

    @Test
    public void whenControllerUpdateStatementStatusThenInvokeDealServiceUpdateStatementStatus() {
        String id = "123";
        controller.updateStatementStatus(id);
        Mockito.verify(dealService, times(1)).updateStatementStatusDocumentsCreated(id);
    }

    @Test
    public void whenControllerGetStatementThenReturnStatementDto() {
        StatementDto expectedStatement = new StatementDto();
        String id = "123";
        Mockito.when(dealService.getStatement(id)).thenReturn(expectedStatement);
        var statement = controller.getStatement(id);
        Mockito.verify(dealService, times(1)).getStatement(id);
        assertThat(statement).isEqualTo(expectedStatement);
    }

    @Test
    public void whenControllerGetStatementsThenReturnListOfStatementsDto() {
        StatementDto statement = new StatementDto();
        var expectedList = List.of(statement);
        Mockito.when(dealService.getStatements()).thenReturn(expectedList);
        var statements = dealService.getStatements();
        Mockito.verify(dealService, times(1)).getStatements();
        assertThat(statements).isEqualTo(expectedList);
    }

}