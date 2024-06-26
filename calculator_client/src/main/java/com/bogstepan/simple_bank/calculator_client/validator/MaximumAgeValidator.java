package com.bogstepan.simple_bank.calculator_client.validator;

import com.bogstepan.simple_bank.calculator_client.annotation.MaximumAge;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MaximumAgeValidator implements ConstraintValidator<MaximumAge, LocalDate> {

    @Value("${validation.maximum_age}")
    private Integer maximumAge;

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) {
        maximumAge = maximumAge == null ? 60 : maximumAge;
        return value == null || value.isAfter(LocalDate.now().minusYears(maximumAge));
    }
}
