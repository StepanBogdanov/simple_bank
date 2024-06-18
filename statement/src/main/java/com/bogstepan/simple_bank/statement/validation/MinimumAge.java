package com.bogstepan.simple_bank.statement.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinimumAgeValidator.class)
public @interface MinimumAge {

    Class<?>[] groups() default {};
    Class<?>[] payload() default {};

    String message() default "Возраст меньше 20 лет";
}