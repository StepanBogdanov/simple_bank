package com.bogstepan.bank.calculator.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class MaximumAgeValidator implements ConstraintValidator<MaximumAge, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) {
        return value == null || value.isAfter(LocalDate.now().minusYears(65));
    }
}
