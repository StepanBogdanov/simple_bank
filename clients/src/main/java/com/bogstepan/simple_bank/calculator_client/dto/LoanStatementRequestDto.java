package com.bogstepan.simple_bank.calculator_client.dto;

import com.bogstepan.simple_bank.calculator_client.annotation.MaximumAge;
import com.bogstepan.simple_bank.calculator_client.annotation.MinimumAge;
import io.swagger.v3.oas.annotations.media.Schema;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class LoanStatementRequestDto {

    @Schema(example = "200000")
    @DecimalMin(value = "30000", message = "Amount is less than 30000")
    BigDecimal amount;

    @Schema(example = "24")
    @Min(value = 6, message = "Term is less than 6")
    Integer term;

    @Schema(example = "Aleksandr")
    @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "The FirstName is longer than 30 characters or is not written in Latin")
    String firstName;

    @Schema(example = "Pushkin")
    @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "The LastName is longer than 30 characters or is not written in Latin")
    String lastName;

    @Schema(example = "Sergeevich")
    @Pattern(regexp = "^[a-zA-Z]{0,30}$", message = "The MiddleName is longer than 30 characters or is not written in Latin")
    String middleName;

    @Schema(example = "pushkin@mail.ru")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Email must match the template")
    String email;

    @Schema(format = "yyyy-mm-dd", example = "1980-06-06")
    @MinimumAge
    @MaximumAge
    LocalDate birthDate;

    @Schema(example = "1111")
    @Pattern(regexp = "^[0-9]{4}$", message = "The passport series must contain exactly 4 digits")
    String passportSeries;

    @Schema(example = "123456")
    @Pattern(regexp = "^[0-9]{6}$", message = "The passport number must contain exactly 6 digits")
    String passportNumber;
}
