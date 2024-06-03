package com.bogstepan.bank.calculator.controller;

import com.bogstepan.bank.calculator.dto.CreditDto;
import com.bogstepan.bank.calculator.dto.LoanOfferDto;
import com.bogstepan.bank.calculator.dto.LoanStatementRequestDto;
import com.bogstepan.bank.calculator.dto.ScoringDataDto;
import com.bogstepan.bank.calculator.exception.InvalidRequestException;
import com.bogstepan.bank.calculator.service.CalculatorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@SpringBootTest
class CalculatorControllerTest {

    @MockBean
    CalculatorService calculatorService;

    @Autowired
    CalculatorController controller;

    @Test
    public void whenCalculateOffersThenGiveOkResponseEntity() {
        var offer = new LoanOfferDto();
        Mockito.when(calculatorService.calculateOffers(any())).thenReturn(List.of(offer));
        var response = controller.calculateOffers(new LoanStatementRequestDto());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(List.of(offer));
        Mockito.verify(calculatorService, times(1)).calculateOffers(any());
    }

    @Test
    public void whenNotCalculateOffersThenGiveInvalidRequestException() {
        Mockito.when(calculatorService.calculateOffers(any())).thenReturn(List.of());
        var exception = assertThrows(InvalidRequestException.class, () ->
                controller.calculateOffers(new LoanStatementRequestDto()));
        assertThat(exception.getMessage()).isEqualTo("Offers calculation error");
        Mockito.verify(calculatorService, times(1)).calculateOffers(any());
    }

    @Test
    public void whenCalculateCreditThenGiveOkResponseEntity() {
        var credit = new CreditDto();
        Mockito.when(calculatorService.calculateCredit(any())).thenReturn(Optional.of(credit));
        var response = controller.calculateCredit(new ScoringDataDto());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(credit);
        Mockito.verify(calculatorService, times(1)).calculateCredit(any());
    }


    @Test
    public void whenNotCalculateCreditThenGiveInvalidRequestException() {
        Mockito.when(calculatorService.calculateCredit(any())).thenReturn(Optional.empty());
        var exception = assertThrows(InvalidRequestException.class, () ->
                controller.calculateCredit(new ScoringDataDto()));
        assertThat(exception.getMessage()).isEqualTo("Credit calculation error");
        Mockito.verify(calculatorService, times(1)).calculateCredit(any());
    }
}