package com.bogstepan.simple_bank.clients.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanOfferDto {

    @Schema(example = "a1079c49-858f-46f7-ad82-54e91cadc06e")
    UUID statementId;
    @Schema(example = "50000")
    BigDecimal requestedAmount;
    @Schema(example = "60600")
    BigDecimal totalAmount;
    @Schema(example = "12")
    Integer term;
    @Schema(example = "5299.56")
    BigDecimal monthlyPayment;
    @Schema(example = "9")
    BigDecimal rate;
    @Schema(example = "true")
    Boolean isInsuranceEnabled;
    @Schema(example = "true")
    Boolean isSalaryClient;
}
