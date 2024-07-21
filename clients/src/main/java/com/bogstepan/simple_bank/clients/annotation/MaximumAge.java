package com.bogstepan.simple_bank.clients.annotation;

import com.bogstepan.simple_bank.clients.validator.MaximumAgeValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaximumAgeValidator.class)
public @interface MaximumAge {

    Class<?>[] groups() default {};
    Class<?>[] payload() default {};

    String message() default "Field 'birthday' more than 65 years from the current day";
}
