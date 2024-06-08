package com.bogstepan.simple_bank.deal.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanStatementRequestDto {

    @Schema(example = "200000")
    BigDecimal amount;
    @Schema(example = "24")
    Integer term;
    @Schema(example = "Aleksandr")
    String firstName;
    @Schema(example = "Pushkin")
    String lastName;
    @Schema(example = "Sergeevich")
    String middleName;
    @Schema(example = "pushkin@mail.ru")
    String email;
    @Schema(format = "yyyy-mm-dd", example = "1980-06-06")
    LocalDate birthDate;
    @Schema(example = "1111")
    String passportSeries;
    @Schema(example = "123456")
    String passportNumber;
}
