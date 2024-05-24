package com.bogstepan.bank.calculator.dto;

import com.bogstepan.bank.calculator.validation.MinimumAge;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "50000")
    @DecimalMin(value = "30000", message = "Field 'amount' is less than 30000")
    BigDecimal amount;
    @Schema(example = "12")
    @Min(value = 6, message = "Field 'term' is less than 6")
    Integer term;
    @Schema(example = "Ivan")
    @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "Field 'firstName' is not written in Latin")
    String firstName;
    @Schema(example = "Ivanov")
    @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "Field 'lastName' is not written in Latin")
    String lastName;
    @Schema(example = "Jovanovich")
    @Pattern(regexp = "^[a-zA-Z]{0,30}$", message = "Field 'middleName' is not written in Latin")
    String middleName;
    @Schema(example = "ivanov@mail.com")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Field 'email' is not a valid email")
    String email;
    @Schema(format = "yyyy-mm-dd", example = "2000-01-01")
    @MinimumAge
    LocalDate birthDate;
    @Schema(example = "1111")
    @Pattern(regexp = "^[0-9]{4}$", message = "Field 'passportSeries' not contains exactly four digits")
    String passportSeries;
    @Schema(example = "123456")
    @Pattern(regexp = "^[0-9]{6}$", message = "Field 'passportNumber' not contains exactly six digits")
    String passportNumber;
}
