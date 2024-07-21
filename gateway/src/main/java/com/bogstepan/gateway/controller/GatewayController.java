package com.bogstepan.gateway.controller;

import com.bogstepan.gateway.feign.DealFeignClient;
import com.bogstepan.gateway.feign.StatementFeignClient;
import com.bogstepan.simple_bank.clients.dto.FinishRegistrationRequestDto;
import com.bogstepan.simple_bank.clients.dto.LoanOfferDto;
import com.bogstepan.simple_bank.clients.dto.LoanStatementRequestDto;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GatewayController {

    private final DealFeignClient dealFeignClient;
    private final StatementFeignClient statementFeignClient;

    @PostMapping("/statement")
    ResponseEntity<List<LoanOfferDto>> calculateOffers(@RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        return statementFeignClient.calculateOffers(loanStatementRequestDto);
    }

    @PostMapping("/statement/select")
    void selectOffer(@RequestBody LoanOfferDto loanOfferDto) {
        dealFeignClient.selectOffer(loanOfferDto);
    }

    @PostMapping("/statement/registration/{statementId}")
    void calculateCredit(@RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto,
                         @PathVariable("statementId") String statementId) {
        dealFeignClient.calculateCredit(finishRegistrationRequestDto, statementId);
    }

    @PostMapping("/document/{statementId}")
    void sendDocs(@PathVariable("statementId") String statementId) {
        dealFeignClient.sendDocs(statementId);
    }

    @PostMapping("/document/{statementId}/sign")
    void signDocs(@PathVariable("statementId") String statementId) {
        dealFeignClient.signDocs(statementId);
    }

    @PostMapping("/document/{statementId}/sign/code")
    void codeDocs(@RequestParam("sesCode") String sesCode, @PathVariable("statementId") String statementId) {
        dealFeignClient.codeDocs(sesCode, statementId);
    }

}
