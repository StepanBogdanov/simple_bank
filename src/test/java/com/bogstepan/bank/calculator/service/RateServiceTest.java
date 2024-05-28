package com.bogstepan.bank.calculator.service;

import com.bogstepan.bank.calculator.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class RateServiceTest {

    ScoringDataDto scoringData;

    @BeforeEach
    void setUp() {
        var employment = new EmploymentDto(
                EmploymentStatus.UNEMPLOYED,
                "123345631",
                BigDecimal.valueOf(30000),
                EmploymentPosition.WORKER,
                36,
                12
        );
        scoringData = new ScoringDataDto(
                BigDecimal.valueOf(50000),
                12,
                "Ivan",
                "Ivanov",
                "",
                Gender.MALE,
                LocalDate.of(2000, 01, 01),
                "1111",
                "123321",
                LocalDate.of(2005, 01, 01),
                "",
                MaritalStatus.SINGLE,
                2,
                employment,
                "124214325215",
                false,
                false
        );
    }

    @Autowired
    private RateService rateService;

    @Test
    public void whenIsInsuranceEnabledAndIsSalaryClientWhenBaseRateDecreaseBySix() {
        var rate = rateService.calculatePreliminaryRate(true, true);
        assertThat(rate).isEqualTo(rateService.getBaseRate().subtract(BigDecimal.valueOf(6)));
    }

    @Test
    public void whenIsNotInsuranceEnabledAndIsNotSalaryClientWhenBaseRateDoesNotDecrease() {
        var rate = rateService.calculatePreliminaryRate(false, false);
        assertThat(rate).isEqualTo(rateService.getBaseRate());
    }

    @Test
    public void whenEmploymentStatusIsBusinessOwnerThenBaseRateIncreaseByTwo() {
        scoringData.getEmployment().setEmploymentStatus(EmploymentStatus.BUSINESS_OWNER);
        var rate = rateService.calculateFinalRate(scoringData);
        assertThat(rate).isEqualTo(rateService.getBaseRate().add(BigDecimal.valueOf(2)));
    }

    @Test
    public void whenEmploymentPositionIsTopManagerThenBaseRateDecreaseByThree() {
        scoringData.getEmployment().setPosition(EmploymentPosition.TOP_MANAGER);
        var rate = rateService.calculateFinalRate(scoringData);
        assertThat(rate).isEqualTo(rateService.getBaseRate().subtract(BigDecimal.valueOf(3)));
    }

    @Test
    public void whenEmploymentMaritalStatusIsDivorcedThenBaseRateIncreaseByOne() {
        scoringData.setMaritalStatus(MaritalStatus.DIVORCED);
        var rate = rateService.calculateFinalRate(scoringData);
        assertThat(rate).isEqualTo(rateService.getBaseRate().add(BigDecimal.ONE));
    }

    @Test
    public void whenEmploymentGenderIsNotBinaryThenBaseRateIncreaseBySeven() {
        scoringData.setGender(Gender.NON_BINARY);
        var rate = rateService.calculateFinalRate(scoringData);
        assertThat(rate).isEqualTo(rateService.getBaseRate().add(BigDecimal.valueOf(7)));
    }


}