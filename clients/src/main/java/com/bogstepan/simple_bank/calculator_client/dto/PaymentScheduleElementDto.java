package com.bogstepan.simple_bank.calculator_client.dto;

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
public class PaymentScheduleElementDto {

    @Schema(example = "1")
    Integer number;
    @Schema(example = "2024-06-25")
    LocalDate date;
    @Schema(example = "5271.61")
    BigDecimal totalPayment;
    @Schema(example = "410.62")
    BigDecimal interestPayment;
    @Schema(example = "4860.99")
    BigDecimal debtPayment;
    @Schema(example = "55739.01")
    BigDecimal remainingDebt;
}
