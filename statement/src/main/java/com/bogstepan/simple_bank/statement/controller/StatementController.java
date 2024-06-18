package com.bogstepan.simple_bank.statement.controller;

import com.bogstepan.simple_bank.statement.dto.LoanOfferDto;
import com.bogstepan.simple_bank.statement.dto.LoanStatementRequestDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Tag(name = "Statement controller")
public interface StatementController {

    @PostMapping("/statement")
    ResponseEntity<List<LoanOfferDto>> calculateOffers(LoanStatementRequestDto loanStatementRequestDto);

    @PostMapping("/satement/offer")
    void selectOffer(LoanOfferDto loanOfferDto);
}
