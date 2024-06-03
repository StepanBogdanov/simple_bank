package com.bogstepan.bank.calculator.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class MinimumAgeValidatorTest {

    private final MinimumAgeValidator validator = new MinimumAgeValidator();

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Value("${validation.minimum_age}")
    private int minimumAge;

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