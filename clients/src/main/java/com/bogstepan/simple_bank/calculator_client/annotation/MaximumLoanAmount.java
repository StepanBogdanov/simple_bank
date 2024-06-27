package com.bogstepan.simple_bank.calculator_client.annotation;

import com.bogstepan.simple_bank.calculator_client.validator.MaximumLoanAmountValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaximumLoanAmountValidator.class)
public @interface MaximumLoanAmount {

    Class<?>[] groups() default {};
    Class<?>[] payload() default {};

    String message() default "Field 'amount' more than 25 employee salaries";
}