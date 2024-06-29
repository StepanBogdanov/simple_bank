package com.bogstepan.simple_bank.statement.service;

import com.bogstepan.simple_bank.calculator_client.dto.LoanStatementRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValidationService {

    private final Validator validator;

    public boolean preScoring(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("Statement pre scoring: {}", loanStatementRequestDto);
        var constraint = validator.validate(loanStatementRequestDto);
        if (!constraint.isEmpty()) {
            for (ConstraintViolation<LoanStatementRequestDto> violation : constraint) {
                log.warn("Pre scoring error: {}", violation.getMessage());
            }
            return false;
        }
        log.info("The statement has successfully passed pre scoring");
        return true;
    }
}
