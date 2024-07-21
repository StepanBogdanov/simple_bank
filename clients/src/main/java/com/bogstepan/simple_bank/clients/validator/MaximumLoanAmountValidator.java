package com.bogstepan.simple_bank.clients.validator;

import com.bogstepan.simple_bank.clients.annotation.MaximumLoanAmount;
import com.bogstepan.simple_bank.clients.dto.ScoringDataDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MaximumLoanAmountValidator implements ConstraintValidator<MaximumLoanAmount, ScoringDataDto> {

    @Value("${validation.solvency_ratio}")
    private Integer solvencyRatio;

    @Override
    public boolean isValid(ScoringDataDto scoringDataDto, ConstraintValidatorContext constraintValidatorContext) {
        solvencyRatio = solvencyRatio == null ? 30 : solvencyRatio;
        BigDecimal maximumLoanAmount = scoringDataDto.getEmployment().getSalary().multiply(BigDecimal.valueOf(solvencyRatio));
        return maximumLoanAmount.compareTo(scoringDataDto.getAmount()) > 0;
    }
}
