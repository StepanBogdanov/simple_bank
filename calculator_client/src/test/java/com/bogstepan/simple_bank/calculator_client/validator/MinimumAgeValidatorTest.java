package com.bogstepan.simple_bank.calculator_client.validator;

import com.bogstepan.simple_bank.calculator_client.util.Props;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class MinimumAgeValidatorTest {

    private final MinimumAgeValidator validator = new MinimumAgeValidator();

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    private int minimumAge = Integer.parseInt(Props.getProperty("validation.minimum_age"));

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