package com.bogstepan.bank.calculator.dto;

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

    Integer number;
    LocalDate date;
    BigDecimal totalPayment;
    BigDecimal interestPayment;
    BigDecimal debtPayment;
    BigDecimal remainingDebt;
}
