package com.bogstepan.bank.calculator.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnumNamePatternValidator implements ConstraintValidator<EnumNamePattern, Enum<?>> {

    private Pattern pattern;

    @Override
    public void initialize(EnumNamePattern annotation) {
        pattern = Pattern.compile(annotation.regexp());
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext constraintValidatorContext) {

        Matcher m = pattern.matcher(value.name());
        return !m.matches();
    }
}
