package com.bogstepan.bank.calculator.validation;

import com.bogstepan.bank.calculator.dto.EmploymentDto;
import com.bogstepan.bank.calculator.dto.EmploymentStatus;
import com.bogstepan.bank.calculator.dto.ScoringDataDto;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MaximumLoanAmountValidatorTest {

    @Autowired
    private Validator validator;

    @Value("${validation.solvency_ratio}")
    private int solvencyRatio;

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