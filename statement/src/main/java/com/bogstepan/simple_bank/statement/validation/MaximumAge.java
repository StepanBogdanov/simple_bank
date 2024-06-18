package com.bogstepan.simple_bank.statement.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaximumAgeValidator.class)
public @interface MaximumAge {

    Class<?>[] groups() default {};
    Class<?>[] payload() default {};

    String message() default "Возраст больше 65 лет";
}

