package com.bogstepan.simple_bank.statement.service;

import com.bogstepan.simple_bank.calculator_client.dto.LoanStatementRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidationServiceTest {

    @Mock
    Validator validator;
    @Mock
    ConstraintViolation<LoanStatementRequestDto> violation;

    @InjectMocks
    ValidationService validationService;

    @Test
    public void whenPreScoringValidRequestThenGiveTrue() {
        var request = new LoanStatementRequestDto();
        Mockito.when(validator.validate(request)).thenReturn(Collections.emptySet());
        validationService.preScoring(request);
        Mockito.verify(validator, Mockito.times(1)).validate(request);
        assertThat(validationService.preScoring(request)).isTrue();
    }

    @Test
    public void whenPreScoringInvalidRequestThenGiveFalse() {
        var request = new LoanStatementRequestDto();
        Mockito.when(validator.validate(request)).thenReturn(Set.of(violation));
        validationService.preScoring(request);
        Mockito.verify(validator, Mockito.times(1)).validate(request);
        assertThat(validationService.preScoring(request)).isFalse();
    }

}