package com.bogstepan.bank.calculator.validation;

import com.bogstepan.bank.calculator.dto.LoanStatementRequestDto;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidationService {

    private final Validator validator;

    public boolean isValidRequest(LoanStatementRequestDto request) {
        var constraint = validator.validate(request);
        if (!constraint.isEmpty()) {
            return false;
        }
        return true;
    }

}
