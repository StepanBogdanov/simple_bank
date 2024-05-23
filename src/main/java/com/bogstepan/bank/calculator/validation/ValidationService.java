package com.bogstepan.bank.calculator.validation;

import com.bogstepan.bank.calculator.dto.LoanStatementRequestDto;
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

    public boolean isValidRequest(LoanStatementRequestDto request) {
        var constraint = validator.validate(request);
        if (!constraint.isEmpty()) {
            for (ConstraintViolation<LoanStatementRequestDto> violation : constraint) {
                log.warn("Request validation error: {}", violation.getMessage());
            }
            return false;
        }
        return true;
    }

}
