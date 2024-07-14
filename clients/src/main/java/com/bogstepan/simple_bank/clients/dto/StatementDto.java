package com.bogstepan.simple_bank.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatementDto {

    ClientDto client;
    CreditDto credit;
    LocalDateTime creationDate;
    LoanOfferDto appliedOffer;
    LocalDateTime signDate;
    String sesCode;
}
