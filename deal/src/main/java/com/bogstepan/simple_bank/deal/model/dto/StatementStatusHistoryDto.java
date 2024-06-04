package com.bogstepan.simple_bank.deal.model.dto;

import com.bogstepan.simple_bank.deal.model.enums.ApplicationStatus;
import com.bogstepan.simple_bank.deal.model.enums.ChangeType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatementStatusHistoryDto {

    ApplicationStatus status;
    LocalDateTime time;
    ChangeType changeType;
}
