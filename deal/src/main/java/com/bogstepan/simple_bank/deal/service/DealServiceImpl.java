package com.bogstepan.simple_bank.deal.service;

import com.bogstepan.simple_bank.deal.exception.RequestException;
import com.bogstepan.simple_bank.deal.feign.CalculatorFeignClient;
import com.bogstepan.simple_bank.deal.model.dto.FinishRegistrationRequestDto;
import com.bogstepan.simple_bank.deal.model.dto.LoanOfferDto;
import com.bogstepan.simple_bank.deal.model.dto.LoanStatementRequestDto;
import com.bogstepan.simple_bank.deal.model.dto.ScoringDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DealServiceImpl implements DealService {

    private final ClientService clientService;
    private final StatementService statementService;
    private final CreditService creditService;
    private final CalculatorFeignClient calculatorFeignClient;

    @Override
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

    @Override
    public void selectOffer(LoanOfferDto loanOfferDto) {
        var statement = statementService.approvedStatement(loanOfferDto);
        log.info("The Statement status with id {} was changed to APPROVED", statement.getStatementId());
    }

    @Override
    public void calculateCredit(FinishRegistrationRequestDto finishRegistrationRequestDto, String statementId) {
        var statement = statementService.getById(statementId);
        var client = clientService.updateClient(finishRegistrationRequestDto, statement.getClient());
        log.info("Client with Id {} was updated", client.getClientId());
        var scoringDataDto = ScoringDataDto.builder()
                .amount(statement.getAppliedOffer().getTotalAmount())
                .term(statement.getAppliedOffer().getTerm())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .middleName(client.getMiddleName())
                .gender(client.getGender())
                .birthDate(client.getBirthDate())
                .passportSeries(client.getPassport().getSeries())
                .passportNumber(client.getPassport().getNumber())
                .passportIssueDate(client.getPassport().getIssueDate())
                .passportIssueBranch(client.getPassport().getIssueBranch())
                .maritalStatus(client.getMaritalStatus())
                .dependentAmount(client.getDependentAmount())
                .employment(finishRegistrationRequestDto.getEmployment())
                .account(client.getAccountNumber())
                .isInsuranceEnabled(statement.getAppliedOffer().getIsInsuranceEnabled())
                .isSalaryClient(statement.getAppliedOffer().getIsSalaryClient())
                .build();
        var creditDto = calculatorFeignClient.calculateCredit(scoringDataDto);
        if (!creditDto.getStatusCode().is2xxSuccessful()) {
            throw new RequestException(String.format("Failed to calculate credit for statement %s", statementId));
        }
        var credit = creditService.saveCredit(creditDto.getBody());
        log.info("New credit is saved in DB: {}", credit);
        statementService.calculatedCreditStatement(statement, credit);
        log.info("The statement status with id {} was changed to CC_APPROVED", statement.getStatementId());
    }
}
