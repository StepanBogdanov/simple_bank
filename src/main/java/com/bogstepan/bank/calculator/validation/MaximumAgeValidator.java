package com.bogstepan.bank.calculator.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

public class MaximumAgeValidator implements ConstraintValidator<MaximumAge, LocalDate> {

    @Value("${validation.maximum_age}")
    private int maximumAge;

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) {
        return value == null || value.isAfter(LocalDate.now().minusYears(maximumAge));
    }
}
