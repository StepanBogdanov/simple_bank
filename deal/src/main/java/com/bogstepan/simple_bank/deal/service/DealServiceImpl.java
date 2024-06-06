package com.bogstepan.simple_bank.deal.service;

import com.bogstepan.simple_bank.deal.feign.CalculatorFeignClient;
import com.bogstepan.simple_bank.deal.model.dto.FinishRegistrationRequestDto;
import com.bogstepan.simple_bank.deal.model.dto.LoanOfferDto;
import com.bogstepan.simple_bank.deal.model.dto.LoanStatementRequestDto;
import com.bogstepan.simple_bank.deal.model.dto.ScoringDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final ClientService clientService;
    private final StatementService statementService;
    private final CreditService creditService;
    private final CalculatorFeignClient calculatorFeignClient;

    @Override
    public List<LoanOfferDto> calculateOffers(LoanStatementRequestDto loanStatementRequestDto) {
        var client = clientService.saveClient(loanStatementRequestDto);
        var statement = statementService.preapprovalStatement(client);
        var offers = calculatorFeignClient.calculateOffers(loanStatementRequestDto).getBody();
        offers.forEach((o) -> o.setStatementId(statement.getStatementId()));
        return offers;
    }

    @Override
    public void selectOffer(LoanOfferDto loanOfferDto) {
        statementService.approvedStatement(loanOfferDto);
    }

    @Override
    public void calculateCredit(FinishRegistrationRequestDto finishRegistrationRequestDto, String statementId) {
        var statement = statementService.getById(statementId);
        var client = clientService.updateClient(finishRegistrationRequestDto, statement.getClient());
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
        var creditDto = calculatorFeignClient.calculateCredit(scoringDataDto).getBody();
        var credit = creditService.saveCredit(creditDto);
        statementService.calculatedCreditStatement(statement, credit);
    }
}
