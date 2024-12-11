package com.bogstepan.simple_bank.statement.service;

import com.bogstepan.simple_bank.clients.dto.LoanOfferDto;
import com.bogstepan.simple_bank.clients.dto.LoanStatementRequestDto;
import com.bogstepan.simple_bank.statement.exception.RequestException;
import com.bogstepan.simple_bank.statement.feign.DealFeignClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class StatementServiceTest {

    @Mock
    ValidationService validationService;
    @Mock
    DealFeignClient feignClient;

    @InjectMocks
    StatementService statementService;

    @Test
    public void whenCalculateOffersThenGiveOffers() {
        var loanStatementRequestDto = new LoanStatementRequestDto();
        var offer = new LoanOfferDto();
        Mockito.when(validationService.preScoring(loanStatementRequestDto)).thenReturn(true);
        Mockito.when(feignClient.calculateOffers(loanStatementRequestDto)).thenReturn(ResponseEntity.ok(List.of(offer)));
        var offers = statementService.calculateOffers(loanStatementRequestDto);
        Mockito.verify(validationService, times(1)).preScoring(loanStatementRequestDto);
        Mockito.verify(feignClient, times(1)).calculateOffers(loanStatementRequestDto);
        assertThat(offers.size()).isEqualTo(1);
        assertThat(offers.get(0)).isEqualTo(offer);
    }

    @Test
    public void whenCalculateOffersAndGiveRequestException() {
        LoanStatementRequestDto request = new LoanStatementRequestDto();
        Mockito.when(validationService.preScoring(request)).thenReturn(true);
        Mockito.when(feignClient.calculateOffers(request)).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        var exception = assertThrows(RequestException.class, () ->
                statementService.calculateOffers(request));
        Mockito.verify(validationService, times(1)).preScoring(request);
        Mockito.verify(feignClient, times(1)).calculateOffers(request);
        assertThat(exception.getMessage()).isEqualTo("Error in receiving loan offers from MS Deal");
    }

    @Test
    public void whenCalculateOffersAndPreScoringFaultThenGiveRequestException() {
        LoanStatementRequestDto request = new LoanStatementRequestDto();
        Mockito.when(validationService.preScoring(request)).thenReturn(false);
        var exception = assertThrows(RequestException.class, () ->
                statementService.calculateOffers(request));
        Mockito.verify(validationService, times(1)).preScoring(request);
        Mockito.verify(feignClient, times(0)).calculateOffers(request);
        assertThat(exception.getMessage()).isEqualTo("Pre scoring error");
    }

    @Test
    public void selectOffer() {
        var offer = new LoanOfferDto();
        statementService.selectOffer(offer);
        Mockito.verify(feignClient, times(1)).selectOffer(offer);
    }

}