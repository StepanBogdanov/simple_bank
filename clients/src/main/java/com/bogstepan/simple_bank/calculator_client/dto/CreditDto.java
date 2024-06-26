package com.bogstepan.simple_bank.calculator_client.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CreditDto {

    @Schema(example = "60600")
    BigDecimal amount;
    @Schema(example = "12")
    Integer term;
    @Schema(example = "5271.61")
    BigDecimal monthlyPayment;
    @Schema(example = "8")
    BigDecimal rate;
    @Schema(example = "65448")
    BigDecimal psk;
    @Schema(example = "true")
    Boolean isInsuranceEnabled;
    @Schema(example = "false")
    Boolean isSalaryClient;
    List<PaymentScheduleElementDto> paymentSchedule;

}
