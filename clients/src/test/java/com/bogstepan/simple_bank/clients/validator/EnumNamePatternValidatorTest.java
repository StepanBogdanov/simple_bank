package com.bogstepan.simple_bank.clients.validator;

import com.bogstepan.simple_bank.clients.dto.EmploymentDto;
import com.bogstepan.simple_bank.clients.enums.EmploymentStatus;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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