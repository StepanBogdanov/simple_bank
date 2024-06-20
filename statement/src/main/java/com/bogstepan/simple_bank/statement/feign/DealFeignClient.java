package com.bogstepan.simple_bank.statement.feign;

import com.bogstepan.simple_bank.statement.dto.LoanOfferDto;
import com.bogstepan.simple_bank.statement.dto.LoanStatementRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "deal", url = "http://localhost:8081")
public interface DealFeignClient {

    @PostMapping("deal/statement")
    ResponseEntity<List<LoanOfferDto>> calculateOffers(@RequestBody LoanStatementRequestDto loanStatementRequestDto);

    @PostMapping("deal/offer/select")
    void selectOffer(@RequestBody LoanOfferDto loanOfferDto);
}
