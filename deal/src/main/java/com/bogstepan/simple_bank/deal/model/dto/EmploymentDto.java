package com.bogstepan.simple_bank.deal.model.dto;

import com.bogstepan.simple_bank.deal.model.enums.EmploymentPosition;
import com.bogstepan.simple_bank.deal.model.enums.EmploymentStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmploymentDto {
    EmploymentStatus employmentStatus;
    String employerINN;
    BigDecimal salary;
    EmploymentPosition position;
    Integer workExperienceTotal;
    Integer workExperienceCurrent;
}
