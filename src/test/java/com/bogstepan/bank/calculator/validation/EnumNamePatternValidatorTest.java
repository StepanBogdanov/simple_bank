package com.bogstepan.bank.calculator.validation;

import com.bogstepan.bank.calculator.dto.EmploymentDto;
import com.bogstepan.bank.calculator.dto.EmploymentStatus;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class EnumNamePatternValidatorTest {

    @Autowired
    private Validator validator;

    @Test
    public void whenEnumTypeMatchesPatternThenGiveNOtEmptyConstraintViolations() {
        var employment = new EmploymentDto();
        employment.setEmploymentStatus(EmploymentStatus.UNEMPLOYED);
        var constraint = validator.validate(employment);
        assertThat(constraint).isNotEmpty();
    }

    @Test
    public void whenEnumTypeNotMatchesPatternThenGiveEmptyConstraintViolations() {
        var employment = new EmploymentDto();
        employment.setEmploymentStatus(EmploymentStatus.BUSINESS_OWNER);
        var constraint = validator.validate(employment);
        assertThat(constraint).isEmpty();
    }

}