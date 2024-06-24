package com.bogstepan.simple_bank.calculator_client.validator;

import com.bogstepan.simple_bank.calculator_client.annotation.MinimumAge;
import com.bogstepan.simple_bank.calculator_client.util.Props;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MinimumAgeValidator implements ConstraintValidator<MinimumAge, LocalDate> {

    private int minimumAge = Integer.parseInt(Props.getProperty("validation.minimum_age"));

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) {
        return value == null || value.isBefore(LocalDate.now().minusYears(minimumAge));
    }
}

