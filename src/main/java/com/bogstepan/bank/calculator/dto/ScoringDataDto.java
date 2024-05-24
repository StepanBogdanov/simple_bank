package com.bogstepan.bank.calculator.dto;

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
public class ScoringDataDto {

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
    @Schema(example = "MALE")
    Gender gender;
    @Schema(example = "2000-01-01")
    LocalDate birthDate;
    @Schema(example = "1111")
    String passportSeries;
    @Schema(example = "123456")
    String passportNumber;
    @Schema(example = "2005-01-01")
    LocalDate passportIssueDate;
    @Schema(example = "UFMS")
    String passportIssueBranch;
    @Schema(example = "MARRIED")
    MaritalStatus maritalStatus;
    @Schema(example = "2")
    Integer dependentAmount;
    EmploymentDto employment;
    @Schema(example = "1541216423")
    String account;
    @Schema(example = "true")
    Boolean isInsuranceEnabled;
    @Schema(example = "false")
    Boolean isSalaryClient;
}
