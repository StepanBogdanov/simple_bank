package com.bogstepan.bank.calculator.service;

import com.bogstepan.simple_bank.calculator_client.dto.EmploymentDto;
import com.bogstepan.simple_bank.calculator_client.dto.ScoringDataDto;
import com.bogstepan.simple_bank.calculator_client.enums.EmploymentPosition;
import com.bogstepan.simple_bank.calculator_client.enums.EmploymentStatus;
import com.bogstepan.simple_bank.calculator_client.enums.Gender;
import com.bogstepan.simple_bank.calculator_client.enums.MaritalStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class RateServiceTest {

    private ScoringDataDto scoringData;

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
                LocalDate.of(2000, 1, 1),
                "1111",
                "123321",
                LocalDate.of(2005, 1, 1),
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
    public void whenEmploymentStatusIsSelfEmployedThenBaseRateIncreaseByOne() {
        scoringData.getEmployment().setEmploymentStatus(EmploymentStatus.SELF_EMPLOYED);
        var rate = rateService.calculateFinalRate(scoringData);
        assertThat(rate).isEqualTo(rateService.getBaseRate().add(BigDecimal.ONE));
    }

    @Test
    public void whenEmploymentPositionIsMiddleManagerThenBaseRateDecreaseByTwo() {
        scoringData.getEmployment().setPosition(EmploymentPosition.MIDDLE_MANAGER);
        var rate = rateService.calculateFinalRate(scoringData);
        assertThat(rate).isEqualTo(rateService.getBaseRate().subtract(BigDecimal.valueOf(2)));
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
    public void whenEmploymentMaritalStatusIsMarriedThenBaseRateDecreaseByThree() {
        scoringData.setMaritalStatus(MaritalStatus.MARRIED);
        var rate = rateService.calculateFinalRate(scoringData);
        assertThat(rate).isEqualTo(rateService.getBaseRate().subtract(BigDecimal.valueOf(3)));
    }

    @Test
    public void whenEmploymentGenderIsMaleAndEmploymentAgeMoreThanMaleLowerAgeLimitThenBaseRateDecreaseByThree() {
        scoringData.setGender(Gender.MALE);
        scoringData.setBirthDate(LocalDate.now().minusYears(rateService.getMaleLowerAgeLimit() + 1));
        var rate = rateService.calculateFinalRate(scoringData);
        assertThat(rate).isEqualTo(rateService.getBaseRate().subtract(BigDecimal.valueOf(3)));
    }

    @Test
    public void whenEmploymentGenderIsFemaleAndEmploymentAgeMoreThanFemaleLowerAgeLimitThenBaseRateDecreaseByThree() {
        scoringData.setGender(Gender.FEMALE);
        scoringData.setBirthDate(LocalDate.now().minusYears(rateService.getFemaleLowerAgeLimit() + 1));
        var rate = rateService.calculateFinalRate(scoringData);
        assertThat(rate).isEqualTo(rateService.getBaseRate().subtract(BigDecimal.valueOf(3)));
    }

    @Test
    public void whenEmploymentGenderIsNotBinaryThenBaseRateIncreaseBySeven() {
        scoringData.setGender(Gender.NON_BINARY);
        var rate = rateService.calculateFinalRate(scoringData);
        assertThat(rate).isEqualTo(rateService.getBaseRate().add(BigDecimal.valueOf(7)));
    }


}