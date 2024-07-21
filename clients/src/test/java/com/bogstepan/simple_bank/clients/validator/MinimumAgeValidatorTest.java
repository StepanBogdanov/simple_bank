package com.bogstepan.simple_bank.clients.validator;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class MinimumAgeValidatorTest {

    private final MinimumAgeValidator validator = new MinimumAgeValidator();

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    private int minimumAge = 18;

    @Test
    public void whenAgeMoreThanEighteenThenGiveTrue() {
        assertThat(validator.isValid(LocalDate.now().minusYears(minimumAge + 1), constraintValidatorContext))
                .isEqualTo(true);
    }

    @Test
    public void whenAgeLessThanEighteenThenGiveFalse() {
        assertThat(validator.isValid(LocalDate.now().minusYears(minimumAge - 1), constraintValidatorContext))
                .isEqualTo(false);
    }
}