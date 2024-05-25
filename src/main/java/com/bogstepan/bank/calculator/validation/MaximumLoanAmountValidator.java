package com.bogstepan.bank.calculator.validation;

import com.bogstepan.bank.calculator.dto.ScoringDataDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class MaximumLoanAmountValidator implements ConstraintValidator<MaximumLoanAmount, ScoringDataDto> {

    @Override
    public boolean isValid(ScoringDataDto scoringDataDto, ConstraintValidatorContext constraintValidatorContext) {
        return scoringDataDto.getAmount().compareTo(
                scoringDataDto.getEmployment().getSalary().multiply(BigDecimal.valueOf(25))) < 0;
    }
}
