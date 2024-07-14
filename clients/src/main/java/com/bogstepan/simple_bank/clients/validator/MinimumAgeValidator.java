package com.bogstepan.simple_bank.clients.validator;

import com.bogstepan.simple_bank.clients.annotation.MinimumAge;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MinimumAgeValidator implements ConstraintValidator<MinimumAge, LocalDate> {

    @Value("${validation.minimum_age}")
    private Integer minimumAge;

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) {
        minimumAge = minimumAge == null ? 18 : minimumAge;
        return value == null || value.isBefore(LocalDate.now().minusYears(minimumAge));
    }
}

