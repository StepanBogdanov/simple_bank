package com.bogstepan.bank.calculator.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinimumAgeValidator.class)
public @interface MinimumAge {

    Class<?>[] groups() default {};
    Class<?>[] payload() default {};

    String message() default "Field 'birthday' less than 18 years from the current day";
}
