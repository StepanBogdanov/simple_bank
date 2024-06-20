package com.bogstepan.simple_bank.statement.service;

import com.bogstepan.simple_bank.statement.dto.LoanOfferDto;
import com.bogstepan.simple_bank.statement.dto.LoanStatementRequestDto;
import com.bogstepan.simple_bank.statement.exception.RequestException;
import com.bogstepan.simple_bank.statement.feign.DealFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatementService {

    private final ValidationService validationService;
    private final DealFeignClient dealFeignClient;

    public List<LoanOfferDto> calculateOffers(LoanStatementRequestDto loanStatementRequestDto) {
        List<LoanOfferDto> offers = new ArrayList<>();
        if (validationService.preScoring(loanStatementRequestDto)) {
            log.info("Запрос на расчет кредитных предложений отправлен в МС Deal {}", loanStatementRequestDto);
            var response = dealFeignClient.calculateOffers(loanStatementRequestDto);
            if (!response.getStatusCode().is2xxSuccessful() || response.getBody().isEmpty()) {
                throw new RequestException("Ошибка получения кредитных предложений из МС Deal");
            }
            log.info("Кредитные предложения получены из МС Deal: {}", response.getBody());
            offers.addAll(response.getBody());
        }
        return offers;
    }

    public void selectOffer(LoanOfferDto loanOfferDto) {
        log.info("Выбранное кредитное предложение по заявке {} отправлено в МС Deal", loanOfferDto.getStatementId());
        dealFeignClient.selectOffer(loanOfferDto);
    }
}
