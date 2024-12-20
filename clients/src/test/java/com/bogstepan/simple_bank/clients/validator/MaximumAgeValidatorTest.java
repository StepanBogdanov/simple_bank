package com.bogstepan.simple_bank.clients.validator;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class MaximumAgeValidatorTest {

    private final MaximumAgeValidator validator = new MaximumAgeValidator();

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    private int maximumAge = 60;

    @Test
    public void whenAgeMoreThanMaximumAgeThenGiveFalse() {
        assertThat(validator.isValid(LocalDate.now().minusYears(maximumAge + 1), constraintValidatorContext))
                .isEqualTo(false);
    }

    @Test
    public void whenAgeLessThanMaximumAgeThanGiveTrue() {
        assertThat(validator.isValid(LocalDate.now().minusYears(maximumAge - 1), constraintValidatorContext))
                .isEqualTo(true);
    }

}