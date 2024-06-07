package com.bogstepan.simple_bank.deal.model.json;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Passport implements Serializable {

    UUID passportId;
    String series;
    String number;
    String issueBranch;
    LocalDate issueDate;

    public Passport(String series, String number) {
        passportId = UUID.randomUUID();
        this.series = series;
        this.number = number;
    }
}
