package com.bogstepan.bank.calculator.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class MaximumAgeValidatorTest {

    private MaximumAgeValidator validator = new MaximumAgeValidator();

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Test
    public void whenAgeMoreThanSixtyFiveThenGiveFalse() {
        assertThat(validator.isValid(LocalDate.of(1950, 1, 1), constraintValidatorContext))
                .isEqualTo(false);
    }

    @Test
    public void whenAgeLessThanSixtyFiveThanGiveTrue() {
        assertThat(validator.isValid(LocalDate.of(2010, 1, 1), constraintValidatorContext))
                .isEqualTo(true);
    }

}