package com.bogstepan.simple_bank.calculator_client.validator;

import com.bogstepan.simple_bank.calculator_client.dto.EmploymentDto;
import com.bogstepan.simple_bank.calculator_client.enums.EmploymentStatus;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.*;

class EnumNamePatternValidatorTest {

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

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