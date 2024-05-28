package com.bogstepan.bank.calculator.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class MinimumAgeValidatorTest {

    private MinimumAgeValidator validator = new MinimumAgeValidator();

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Test
    public void whenAgeMoreThanEighteenThenGiveTrue() {
        assertThat(validator.isValid(LocalDate.of(2000, 1, 1), constraintValidatorContext))
                .isEqualTo(true);
    }

    @Test
    public void whenAgeLessThanEighteenThenGiveFalse() {
        assertThat(validator.isValid(LocalDate.of(2010, 1, 1), constraintValidatorContext))
                .isEqualTo(false);
    }
}