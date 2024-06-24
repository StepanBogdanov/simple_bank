package com.bogstepan.simple_bank.calculator_client.validator;

import com.bogstepan.simple_bank.calculator_client.annotation.MaximumLoanAmount;
import com.bogstepan.simple_bank.calculator_client.dto.ScoringDataDto;
import com.bogstepan.simple_bank.calculator_client.util.Props;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MaximumLoanAmountValidator implements ConstraintValidator<MaximumLoanAmount, ScoringDataDto> {

    private int solvencyRatio = Integer.parseInt(Props.getProperty("validation.solvency_ratio"));

    @Override
    public boolean isValid(ScoringDataDto scoringDataDto, ConstraintValidatorContext constraintValidatorContext) {
        BigDecimal maximumLoanAmount = scoringDataDto.getEmployment().getSalary().multiply(BigDecimal.valueOf(solvencyRatio));
        return maximumLoanAmount.compareTo(scoringDataDto.getAmount()) > 0;
    }
}
