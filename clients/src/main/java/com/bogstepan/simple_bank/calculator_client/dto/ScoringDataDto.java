package com.bogstepan.simple_bank.calculator_client.dto;

import com.bogstepan.simple_bank.calculator_client.annotation.MaximumAge;
import com.bogstepan.simple_bank.calculator_client.annotation.MaximumLoanAmount;
import com.bogstepan.simple_bank.calculator_client.annotation.MinimumAge;
import com.bogstepan.simple_bank.calculator_client.enums.Gender;
import com.bogstepan.simple_bank.calculator_client.enums.MaritalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
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
@MaximumLoanAmount
public class ScoringDataDto {

    @Schema(example = "50000")
    @DecimalMin(value = "30000", message = "Field 'amount' is less than 30000")
    BigDecimal amount;

    @Schema(example = "12")
    @Min(value = 6, message = "Field 'term' is less than 6")
    Integer term;

    @Schema(example = "Ivan")
    @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "The FirstName is longer than 30 characters or is not written in Latin")
    String firstName;

    @Schema(example = "Ivanov")
    @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "The LastName is longer than 30 characters or is not written in Latin")
    String lastName;

    @Schema(example = "Jovanovich")
    @Pattern(regexp = "^[a-zA-Z]{0,30}$", message = "The MiddleName is longer than 30 characters or is not written in Latin")
    String middleName;

    @Schema(example = "MALE")
    Gender gender;

    @Schema(format = "yyyy-mm-dd", example = "2000-01-01")
    @MinimumAge
    @MaximumAge
    LocalDate birthDate;

    @Schema(example = "1111")
    @Pattern(regexp = "^[0-9]{4}$", message = "The passport series must contain exactly 4 digits")
    String passportSeries;

    @Schema(example = "123456")
    @Pattern(regexp = "^[0-9]{6}$", message = "The passport number must contain exactly 6 digits")
    String passportNumber;

    @Schema(example = "2005-01-01")
    LocalDate passportIssueDate;

    @Schema(example = "UFMS")
    String passportIssueBranch;

    @Schema(example = "MARRIED")
    MaritalStatus maritalStatus;

    @Schema(example = "2")
    Integer dependentAmount;

    @Valid
    EmploymentDto employment;

    @Schema(example = "1541216423")
    String account;

    @Schema(example = "true")
    Boolean isInsuranceEnabled;

    @Schema(example = "false")
    Boolean isSalaryClient;
}
