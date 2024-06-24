package com.bogstepan.simple_bank.calculator_client.validator;

import com.bogstepan.simple_bank.calculator_client.annotation.MaximumAge;
import com.bogstepan.simple_bank.calculator_client.util.Props;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MaximumAgeValidator implements ConstraintValidator<MaximumAge, LocalDate> {

    private int maximumAge = Integer.parseInt(Props.getProperty("validation.maximum_age"));

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) {
        return value == null || value.isAfter(LocalDate.now().minusYears(maximumAge));
    }
}
