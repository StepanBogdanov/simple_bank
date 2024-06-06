package com.bogstepan.simple_bank.deal.model.json;

import com.bogstepan.simple_bank.deal.model.enums.EmploymentPosition;
import com.bogstepan.simple_bank.deal.model.enums.EmploymentStatus;
import jakarta.persistence.Transient;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Employment implements Serializable {

    @Transient
    UUID employmentId = UUID.randomUUID();
    EmploymentStatus status;
    String employerInn;
    BigDecimal salary;
    EmploymentPosition position;
    Integer workExperienceTotal;
    Integer workExperienceCurrent;
}
