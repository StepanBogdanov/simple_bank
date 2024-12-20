package com.bogstepan.simple_bank.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassportDto {

    String series;
    String number;
    String issueBranch;
    LocalDate issueDate;
}
