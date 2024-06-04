package com.bogstepan.simple_bank.deal.model.json;

import com.bogstepan.simple_bank.deal.model.enums.EmploymentPosition;
import com.bogstepan.simple_bank.deal.model.enums.EmploymentStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employment implements Serializable {

    UUID employmentId;
    EmploymentStatus status;
    String employerInn;
    BigDecimal salary;
    EmploymentPosition position;
    Integer workExperienceTotal;
    Integer workExperienceCurrent;
}
