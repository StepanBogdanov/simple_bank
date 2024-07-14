package com.bogstepan.simple_bank.clients.dto;

import com.bogstepan.simple_bank.clients.annotation.EnumNamePattern;
import com.bogstepan.simple_bank.clients.enums.EmploymentPosition;
import com.bogstepan.simple_bank.clients.enums.EmploymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
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
    @EnumNamePattern(regexp = "UNEMPLOYED", message = "Field 'EmploymentStatus' is UNEMPLOYED")
    EmploymentStatus employmentStatus;

    @Schema(example = "12364312")
    String employerINN;

    @Schema(example = "30000")
    BigDecimal salary;

    @Schema(example = "WORKER")
    EmploymentPosition position;

    @Schema(example = "36")
    @Min(value = 12, message = "Field 'workExperienceTotal' is less than 12")
    Integer workExperienceTotal;

    @Schema(example = "12")
    @Min(value = 3, message = "Field 'workExperienceCurrent' is less than 3")
    Integer workExperienceCurrent;
}
