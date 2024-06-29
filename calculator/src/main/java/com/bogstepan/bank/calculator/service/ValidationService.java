package com.bogstepan.bank.calculator.service;

import com.bogstepan.simple_bank.calculator_client.dto.ScoringDataDto;
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

    public boolean scoring(ScoringDataDto scoringDataDto) {
        var constraint = validator.validate(scoringDataDto);
        if (!constraint.isEmpty()) {
            for (ConstraintViolation<ScoringDataDto> violation : constraint) {
                log.warn("Credit denied: {}", violation.getMessage());
            }
            return false;
        }
        return true;
    }

}
