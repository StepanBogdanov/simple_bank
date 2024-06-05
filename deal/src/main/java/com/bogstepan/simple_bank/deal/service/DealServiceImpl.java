package com.bogstepan.simple_bank.deal.service;

import com.bogstepan.simple_bank.deal.feign.CalculatorFeignClient;
import com.bogstepan.simple_bank.deal.model.dto.LoanOfferDto;
import com.bogstepan.simple_bank.deal.model.dto.LoanStatementRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final ClientService clientService;
    private final StatementService statementService;
    private final CalculatorFeignClient calculatorFeignClient;

    @Override
    public List<LoanOfferDto> calculateOffers(LoanStatementRequestDto loanStatementRequestDto) {
        var client = clientService.saveClient(loanStatementRequestDto);
        statementService.preapprovalStatement(client);
        return calculatorFeignClient.calculateOffers(loanStatementRequestDto).getBody();
    }
}
