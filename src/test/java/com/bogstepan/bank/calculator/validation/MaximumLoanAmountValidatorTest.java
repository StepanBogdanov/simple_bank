package com.bogstepan.bank.calculator.validation;

import com.bogstepan.bank.calculator.dto.EmploymentDto;
import com.bogstepan.bank.calculator.dto.EmploymentStatus;
import com.bogstepan.bank.calculator.dto.ScoringDataDto;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MaximumLoanAmountValidatorTest {

    @Autowired
    private Validator validator;

    @Test
    public void whenAmountLessThan25SalariesThenGiveEmptyConstraintViolations() {
        var scoringData = new ScoringDataDto();
        var employment = new EmploymentDto();
        employment.setSalary(BigDecimal.valueOf(30000));
        employment.setEmploymentStatus(EmploymentStatus.BUSINESS_OWNER);
        scoringData.setAmount(BigDecimal.valueOf(100000));
        scoringData.setEmployment(employment);
        var constraint = validator.validate(scoringData);
        assertThat(constraint).isEmpty();
    }

    @Test
    public void whenAmountMoreThan25SalariesThenGiveEmptyConstraintViolations() {
        var scoringData = new ScoringDataDto();
        var employment = new EmploymentDto();
        employment.setSalary(BigDecimal.valueOf(300));
        employment.setEmploymentStatus(EmploymentStatus.BUSINESS_OWNER);
        scoringData.setAmount(BigDecimal.valueOf(100000));
        scoringData.setEmployment(employment);
        var constraint = validator.validate(scoringData);
        assertThat(constraint).isNotEmpty();
    }

}