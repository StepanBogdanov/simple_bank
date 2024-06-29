package com.bogstepan.bank.calculator.service;

import com.bogstepan.simple_bank.calculator_client.dto.EmploymentDto;
import com.bogstepan.simple_bank.calculator_client.dto.ScoringDataDto;
import com.bogstepan.simple_bank.calculator_client.enums.EmploymentPosition;
import com.bogstepan.simple_bank.calculator_client.enums.EmploymentStatus;
import com.bogstepan.simple_bank.calculator_client.enums.Gender;
import com.bogstepan.simple_bank.calculator_client.enums.MaritalStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ValidationServiceTest {

    @Autowired
    private ValidationService service;

    @Test
    public void whenValidScoringDataThenGiveTrue() {
        var scoringData = new ScoringDataDto(
                BigDecimal.valueOf(50000),
                12,
                "Ivan",
                "Ivanov",
                "",
                Gender.MALE,
                LocalDate.of(1990, 1, 1),
                "1111",
                "123456",
                LocalDate.of(2005, 1, 1),
                "UFMS",
                MaritalStatus.MARRIED,
                2,
                new EmploymentDto(
                        EmploymentStatus.BUSINESS_OWNER,
                        "123456789",
                        BigDecimal.valueOf(30000),
                        EmploymentPosition.TOP_MANAGER,
                        36,
                        12
                ),
                "24123543654",
                true,
                true
        );
        assertThat(service.scoring(scoringData)).isEqualTo(true);
    }

    @Test
    public void whenInvalidScoringDataThenGiveFalse() {
        var scoringData = new ScoringDataDto(
                BigDecimal.valueOf(50000000),
                12,
                "Ivan",
                "Ivanov",
                "",
                Gender.MALE,
                LocalDate.of(1990, 1, 1),
                "1111",
                "123456",
                LocalDate.of(2005, 1, 1),
                "UFMS",
                MaritalStatus.MARRIED,
                2,
                new EmploymentDto(
                        EmploymentStatus.BUSINESS_OWNER,
                        "123456789",
                        BigDecimal.valueOf(30000),
                        EmploymentPosition.TOP_MANAGER,
                        36,
                        12
                ),
                "24123543654",
                true,
                true
        );
        assertThat(service.scoring(scoringData)).isEqualTo(false);
    }

}