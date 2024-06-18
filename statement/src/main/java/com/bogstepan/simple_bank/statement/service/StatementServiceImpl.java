package com.bogstepan.simple_bank.statement.service;

import com.bogstepan.simple_bank.statement.dto.LoanOfferDto;
import com.bogstepan.simple_bank.statement.dto.LoanStatementRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatementServiceImpl implements StatementService {

    private final ValidationService validationService;

    @Override
    public List<LoanOfferDto> calculateOffers(LoanStatementRequestDto loanStatementRequestDto) {
        if (validationService.preScoring(loanStatementRequestDto)) {
            log.info("Запрос на расчет кредитных предложений отправлен в сервис Deal");
//            todo: feign
        }
        return List.of();
    }

    @Override
    public void selectOffer(LoanOfferDto loanOfferDto) {

    }
}
