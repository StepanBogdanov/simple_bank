package com.bogstepan.simple_bank.calculator_client.dto;

import com.bogstepan.simple_bank.calculator_client.enums.Gender;
import com.bogstepan.simple_bank.calculator_client.enums.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

    String firstName;
    String lastName;
    String middleName;
    LocalDate birthDate;
    String email;
    Gender gender;
    MaritalStatus maritalStatus;
    Integer dependentAmount;
    PassportDto passport;
    EmploymentDto employment;
    String accountNumber;
}
