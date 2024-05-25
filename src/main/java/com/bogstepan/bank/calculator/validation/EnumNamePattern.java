package com.bogstepan.bank.calculator.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumNamePatternValidator.class)
public @interface EnumNamePattern {

    String regexp();
    String message();

    Class<?>[] groups() default {};
    Class<?>[] payload() default {};

}
