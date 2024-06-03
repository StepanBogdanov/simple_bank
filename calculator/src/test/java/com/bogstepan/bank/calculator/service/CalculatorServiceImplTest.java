package com.bogstepan.bank.calculator.service;

import com.bogstepan.bank.calculator.dto.LoanStatementRequestDto;
import com.bogstepan.bank.calculator.dto.ScoringDataDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;

@SpringBootTest
class CalculatorServiceImplTest {

    @MockBean
    ValidationService validationService;
    @MockBean
    ScheduleService scheduleService;
    @MockBean
    RateService rateService;

    @Autowired
    CalculatorServiceImpl calculatorService;

    @Test
    public void whenValidRequestThenCalculateOffers() {
        var request = new LoanStatementRequestDto(BigDecimal.valueOf(50000), 12, "",
                "", "", "", LocalDate.now(), "", "");
        Mockito.when(rateService.calculatePreliminaryRate(anyBoolean(), anyBoolean())).thenReturn(new BigDecimal("15"));
        Mockito.when(validationService.preScoring(request)).thenReturn(true);
        var offers = calculatorService.calculateOffers(request);
        assertThat(offers.size()).isEqualTo(4);
        assertThat(offers.get(0).getMonthlyPayment().setScale(0, RoundingMode.HALF_UP)
                .compareTo(BigDecimal.valueOf(4513))).isEqualTo(0);
    }

    @Test
    public void whenInvalidRequestThenNotCalculateOffers() {
        var request = new LoanStatementRequestDto();
        Mockito.when(validationService.preScoring(request)).thenReturn(false);
        var offers = calculatorService.calculateOffers(request);
        assertThat(offers).isEmpty();
    }

    @Test
    public void whenValidScoringDataThenCalculateCredit() {
        var scoringData = new ScoringDataDto();
        scoringData.setAmount(new BigDecimal("50000"));
        scoringData.setTerm(12);
        scoringData.setIsInsuranceEnabled(false);
        Mockito.when(rateService.calculateFinalRate(any())).thenReturn(new BigDecimal("15"));
        Mockito.when(validationService.scoring(any())).thenReturn(true);
        var creditDto = calculatorService.calculateCredit(scoringData);
        assertThat(creditDto.isPresent()).isTrue();
        assertThat(creditDto.get().getMonthlyPayment().setScale(0, RoundingMode.HALF_UP)
                .compareTo(BigDecimal.valueOf(4513))).isEqualTo(0);
    }

    @Test
    public void whenInvalidScoringDataThenNotCalculateCredit() {
        var scoringData = new ScoringDataDto();
        Mockito.when(validationService.scoring(any())).thenReturn(false);
        Mockito.when(rateService.calculateFinalRate(any())).thenReturn(new BigDecimal("15"));
        var creditDto = calculatorService.calculateCredit(scoringData);
        assertThat(creditDto.isEmpty()).isTrue();
    }

}