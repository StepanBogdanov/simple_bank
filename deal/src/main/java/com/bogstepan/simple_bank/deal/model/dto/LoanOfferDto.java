package com.bogstepan.simple_bank.deal.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class LoanOfferDto {

    @Transient
    @Schema(example = "2fbfeaef-ab99-49fa-951b-aa44bd58d309")
    UUID statementId;
    @Schema(example = "200000")
    BigDecimal requestedAmount;
    @Schema(example = "214800")
    BigDecimal totalAmount;
    @Schema(example = "24")
    Integer term;
    @Schema(example = "9911.53")
    BigDecimal monthlyPayment;
    @Schema(example = "10")
    BigDecimal rate;
    @Schema(example = "true")
    Boolean isInsuranceEnabled;
    @Schema(example = "false")
    Boolean isSalaryClient;
}
