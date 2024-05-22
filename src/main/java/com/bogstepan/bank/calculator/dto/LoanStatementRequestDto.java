package com.bogstepan.bank.calculator.dto;

import com.bogstepan.bank.calculator.validation.MinimumAge;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanStatementRequestDto {

    @DecimalMin(value = "30000", message = "Field 'amount' is less than 30000")
    BigDecimal amount;
    @Min(value = 6, message = "Field 'term' is less than 6")
    Integer term;
    @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "Field 'firstName' is not written in Latin")
    String firstName;
    @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "Field 'lastName' is not written in Latin")
    String lastName;
    @Pattern(regexp = "^[a-zA-Z]{0,30}$", message = "Field 'middleName' is not written in Latin")
    String middleName;
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Field 'email' is not a valid email")
    String email;
    @MinimumAge
    LocalDate birthDate;
    @Pattern(regexp = "^[0-9]{4}$", message = "Field 'passportSeries' not contains exactly four digits")
    String passportSeries;
    @Pattern(regexp = "^[0-9]{6}$", message = "Field 'passportNumber' not contains exactly six digits")
    String passportNumber;
}
