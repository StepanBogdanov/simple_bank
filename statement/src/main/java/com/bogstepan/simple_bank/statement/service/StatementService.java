package com.bogstepan.simple_bank.statement.service;

import com.bogstepan.simple_bank.clients.dto.LoanOfferDto;
import com.bogstepan.simple_bank.clients.dto.LoanStatementRequestDto;
import com.bogstepan.simple_bank.statement.exception.RequestException;
import com.bogstepan.simple_bank.statement.feign.DealFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatementService {

    private final ValidationService validationService;
    private final DealFeignClient dealFeignClient;

    public List<LoanOfferDto> calculateOffers(LoanStatementRequestDto loanStatementRequestDto) {
        if (!validationService.preScoring(loanStatementRequestDto)) {
            throw new RequestException("Pre scoring error");
        }
        log.info("A request for calculation of loan offers has been sent to MS Deal {}", loanStatementRequestDto);
        var response = dealFeignClient.calculateOffers(loanStatementRequestDto);
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody().isEmpty()) {
            throw new RequestException("Error in receiving loan offers from MS Deal");
        }
        log.info("Loan offers received from MS Deal: {}", response.getBody());
        return response.getBody();
    }

    public void selectOffer(LoanOfferDto loanOfferDto) {
        log.info("The selected loan offer {} has been sent to MS Deal", loanOfferDto.getStatementId());
        dealFeignClient.selectOffer(loanOfferDto);
    }
}
