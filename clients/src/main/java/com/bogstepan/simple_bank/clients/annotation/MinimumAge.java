package com.bogstepan.simple_bank.clients.annotation;

import com.bogstepan.simple_bank.clients.validator.MinimumAgeValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinimumAgeValidator.class)
public @interface MinimumAge {

    Class<?>[] groups() default {};
    Class<?>[] payload() default {};

    String message() default "Field 'birthday' less than 20 years from the current day";
}

