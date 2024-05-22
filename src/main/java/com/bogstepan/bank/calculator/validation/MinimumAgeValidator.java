package com.bogstepan.bank.calculator.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class MinimumAgeValidator implements ConstraintValidator<MinimumAge, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) {
        return value == null || value.isBefore(LocalDate.now().minusYears(18));
    }
}
