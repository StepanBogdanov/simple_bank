package com.bogstepan.bank.calculator.dto;

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

    BigDecimal amount;
    Integer term;
    String firstName;
    String lastName;
    String middleName;
    String email;
    LocalDate birthDate;
    String passportSeries;
    String passportNumber;
}
