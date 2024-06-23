package com.bogstepan.simple_bank.calculator_client.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanStatementRequestDto {

    @Schema(example = "50000")
    BigDecimal amount;

    @Schema(example = "12")
    Integer term;

    @Schema(example = "Ivan")
    String firstName;

    @Schema(example = "Ivanov")
    String lastName;

    @Schema(example = "Jovanovich")
    String middleName;

    @Schema(example = "ivanov@mail.com")
    String email;

    @Schema(format = "yyyy-mm-dd", example = "2000-01-01")
    LocalDate birthDate;

    @Schema(example = "1111")
    String passportSeries;

    @Schema(example = "123456")
    String passportNumber;
}
