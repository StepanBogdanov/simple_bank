package com.bogstepan.simple_bank.statement.service;

import com.bogstepan.simple_bank.statement.dto.LoanStatementRequestDto;
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
        log.info("Прескоринг заявки: {}", loanStatementRequestDto);
        var constraint = validator.validate(loanStatementRequestDto);
        if (!constraint.isEmpty()) {
            for (ConstraintViolation<LoanStatementRequestDto> violation : constraint) {
                log.warn("Ошибка прескоринга: {}", violation.getMessage());
            }
            return false;
        }
        log.info("Заявка успешно прошла прескоринг");
        return true;
    }
}
