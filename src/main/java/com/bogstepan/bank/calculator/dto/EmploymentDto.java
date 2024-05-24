package com.bogstepan.bank.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmploymentDto {

    @Schema(example = "SELF_EMPLOYED")
    EmploymentStatus employmentStatus;
    @Schema(example = "12364312")
    String employerINN;
    @Schema(example = "30000")
    BigDecimal salary;
    @Schema(example = "WORKER")
    EmploymentPosition position;
    @Schema(example = "36")
    Integer workExperienceTotal;
    @Schema(example = "12")
    Integer workExperienceCurrent;
}
