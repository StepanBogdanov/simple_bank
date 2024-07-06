package com.bogstepan.simple_bank.deal.service;

import com.bogstepan.simple_bank.calculator_client.dto.FinishRegistrationRequestDto;
import com.bogstepan.simple_bank.calculator_client.dto.LoanOfferDto;
import com.bogstepan.simple_bank.calculator_client.dto.LoanStatementRequestDto;
import com.bogstepan.simple_bank.deal.exception.RequestException;
import com.bogstepan.simple_bank.deal.feign.CalculatorFeignClient;
import com.bogstepan.simple_bank.deal.mapping.ScoringDataMapper;
import com.bogstepan.simple_bank.deal.supplier.KafkaSupplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DealService {

    private final ClientService clientService;
    private final StatementService statementService;
    private final CreditService creditService;
    private final CalculatorFeignClient calculatorFeignClient;
    private final ScoringDataMapper scoringDataMapper;
    private final KafkaSupplier kafkaSupplier;

    public List<LoanOfferDto> calculateOffers(LoanStatementRequestDto loanStatementRequestDto) {
        var client = clientService.saveNewClient(loanStatementRequestDto);
        log.info("New client is saved in DB: {}", client);
        var statement = statementService.preapprovalStatement(client);
        log.info("New statement is saved to DB: {}", statement);
        var offers = calculatorFeignClient.calculateOffers(loanStatementRequestDto);
        if (!offers.getStatusCode().is2xxSuccessful() || offers.getBody().isEmpty()) {
            throw new RequestException(String.format("Failed to calculate offers for statement %s", statement.getStatementId()));
        }
        offers.getBody().forEach((o) -> o.setStatementId(statement.getStatementId()));
        return offers.getBody();
    }

    public void selectOffer(LoanOfferDto loanOfferDto) {
        var statement = statementService.approvedStatement(loanOfferDto);
        log.info("The Statement status with id {} was changed to APPROVED", statement.getStatementId());
        kafkaSupplier.finishRegistrationRequest(statement.getStatementId().toString());
    }

    public void calculateCredit(FinishRegistrationRequestDto finishRegistrationRequestDto, String statementId) {
        var statement = statementService.getById(statementId);
        var client = clientService.updateClient(statement.getClient(), finishRegistrationRequestDto);
        log.info("Client with Id {} was updated", client.getClientId());
        var scoringDataDto = scoringDataMapper.toScoringDataDto(statement, client);
        var creditDto = calculatorFeignClient.calculateCredit(scoringDataDto);
        if (!creditDto.getStatusCode().is2xxSuccessful()) {
            throw new RequestException(String.format("Failed to calculate credit for statement %s", statementId));
        }
        var credit = creditService.saveCredit(creditDto.getBody());
        log.info("New credit is saved in DB: {}", credit);
        statementService.calculatedCreditStatement(statement, credit);
        log.info("The statement status with id {} was changed to CC_APPROVED", statement.getStatementId());
        kafkaSupplier.createDocumentsRequest(statementId);
    }
}
