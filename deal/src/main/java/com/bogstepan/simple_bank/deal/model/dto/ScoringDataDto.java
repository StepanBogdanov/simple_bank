package com.bogstepan.simple_bank.deal.model.dto;

import com.bogstepan.simple_bank.deal.model.enums.Gender;
import com.bogstepan.simple_bank.deal.model.enums.MaritalStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ScoringDataDto {

    BigDecimal amount;
    Integer term;
    String firstName;
    String lastName;
    String middleName;
    Gender gender;
    LocalDate birthDate;
    String passportSeries;
    String passportNumber;
    LocalDate passportIssueDate;
    String passportIssueBranch;
    MaritalStatus maritalStatus;
    Integer dependentAmount;
    EmploymentDto employment;
    String account;
    Boolean isInsuranceEnabled;
    Boolean isSalaryClient;
}
