package com.bogstepan.simple_bank.clients.validator;

import com.bogstepan.simple_bank.clients.dto.EmploymentDto;
import com.bogstepan.simple_bank.clients.dto.ScoringDataDto;
import com.bogstepan.simple_bank.clients.enums.EmploymentStatus;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class MaximumLoanAmountValidatorTest {

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private int solvencyRatio = 30;

    @Test
    public void whenAmountLessMaximumLoanAmountThenGiveEmptyConstraintViolations() {
        var scoringData = new ScoringDataDto();
        var employment = new EmploymentDto();
        var salary = new BigDecimal("30000");
        var maximumLoanAmount = salary.multiply(BigDecimal.valueOf(solvencyRatio));
        employment.setSalary(salary);
        employment.setEmploymentStatus(EmploymentStatus.BUSINESS_OWNER);
        scoringData.setAmount(maximumLoanAmount.subtract(BigDecimal.ONE));
        scoringData.setEmployment(employment);
        var constraint = validator.validate(scoringData);
        assertThat(constraint).isEmpty();
    }

    @Test
    public void whenAmountMoreMaximumLoanAmountThenGiveEmptyConstraintViolations() {
        var scoringData = new ScoringDataDto();
        var employment = new EmploymentDto();
        var salary = new BigDecimal("30000");
        var maximumLoanAmount = salary.multiply(BigDecimal.valueOf(solvencyRatio));
        employment.setSalary(salary);
        employment.setEmploymentStatus(EmploymentStatus.BUSINESS_OWNER);
        scoringData.setAmount(maximumLoanAmount.add(BigDecimal.ONE));
        scoringData.setEmployment(employment);
        var constraint = validator.validate(scoringData);
        assertThat(constraint).isNotEmpty();
    }

}