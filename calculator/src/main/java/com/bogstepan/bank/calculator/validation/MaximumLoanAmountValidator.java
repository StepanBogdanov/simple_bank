package com.bogstepan.bank.calculator.validation;

import com.bogstepan.bank.calculator.dto.ScoringDataDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public class MaximumLoanAmountValidator implements ConstraintValidator<MaximumLoanAmount, ScoringDataDto> {

    @Value("${validation.solvency_ratio}")
    private int solvencyRatio;

    @Override
    public boolean isValid(ScoringDataDto scoringDataDto, ConstraintValidatorContext constraintValidatorContext) {
        var maximumLoanAmount = scoringDataDto.getEmployment().getSalary().multiply(BigDecimal.valueOf(solvencyRatio));
        return maximumLoanAmount.compareTo(scoringDataDto.getAmount()) > 0;
    }
}
