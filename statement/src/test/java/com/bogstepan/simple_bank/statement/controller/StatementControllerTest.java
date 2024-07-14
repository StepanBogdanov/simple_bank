package com.bogstepan.simple_bank.statement.controller;

import com.bogstepan.simple_bank.clients.dto.LoanOfferDto;
import com.bogstepan.simple_bank.clients.dto.LoanStatementRequestDto;
import com.bogstepan.simple_bank.statement.service.StatementService;
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
class StatementControllerTest {

    @Mock
    StatementService statementService;

    @InjectMocks
    StatementController controller;

    @Test
    public void whenCalculateOffersThenGiveOkResponseEntity() {
        var offer = new LoanOfferDto();
        Mockito.when(statementService.calculateOffers(any())).thenReturn(List.of(offer));
        var response = controller.calculateOffers(new LoanStatementRequestDto());
        Mockito.verify(statementService, times(1)).calculateOffers(any());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(List.of(offer));
    }

    @Test
    public void whenSelectOfferThenInvokeStatementServiceSelectOffer() {
        var offer = new LoanOfferDto();
        controller.selectOffer(offer);
        Mockito.verify(statementService, times(1)).selectOffer(offer);
    }
}