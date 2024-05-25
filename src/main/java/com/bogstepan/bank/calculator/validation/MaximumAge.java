package com.bogstepan.bank.calculator.validation;

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
