package com.bogstepan.simple_bank.clients.dto;

import com.bogstepan.simple_bank.clients.enums.Gender;
import com.bogstepan.simple_bank.clients.enums.MaritalStatus;
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
