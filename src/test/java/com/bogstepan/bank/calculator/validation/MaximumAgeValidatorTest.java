package com.bogstepan.bank.calculator.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class MaximumAgeValidatorTest {

    private final MaximumAgeValidator validator = new MaximumAgeValidator();

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Value("${validation.maximum_age}")
    private int maximumAge;

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