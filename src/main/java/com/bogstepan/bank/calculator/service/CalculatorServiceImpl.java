package com.bogstepan.bank.calculator.service;

import com.bogstepan.bank.calculator.dto.CreditDto;
import com.bogstepan.bank.calculator.dto.LoanOfferDto;
import com.bogstepan.bank.calculator.dto.LoanStatementRequestDto;
import com.bogstepan.bank.calculator.dto.ScoringDataDto;
import com.bogstepan.bank.calculator.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CalculatorServiceImpl implements CalculatorService {

    private static final BigDecimal BASE_RATE = BigDecimal.valueOf(15);

    private final ScheduleService scheduleService;
    private final ValidationService validationService;

    @Override
    public List<LoanOfferDto> calculateOffers(LoanStatementRequestDto loanStatementRequestDto) {
        List<LoanOfferDto> offers = new ArrayList<>();
        if (preScoring(loanStatementRequestDto)) {
            offers.add(calculateOffer(loanStatementRequestDto, false, false));
            offers.add(calculateOffer(loanStatementRequestDto, false, true));
            offers.add(calculateOffer(loanStatementRequestDto, true, false));
            offers.add(calculateOffer(loanStatementRequestDto,true, true));
        }
        return offers;
    }

    @Override
    public CreditDto calculateCredit(ScoringDataDto scoringDataDto) {
        var rate = BASE_RATE;
        var credit = new CreditDto();
        if (scoring(scoringDataDto)) {
            var totalAmount = calculateTotalAmount(scoringDataDto.getAmount(), scoringDataDto.getTerm(),
                    scoringDataDto.getIsInsuranceEnabled());
            var monthlyPayment = calculateMonthlyPayment(totalAmount, rate, scoringDataDto.getTerm());
            credit.setAmount(totalAmount);
            credit.setTerm(scoringDataDto.getTerm());
            credit.setMonthlyPayment(monthlyPayment);
            credit.setRate(rate);
            credit.setPsk(calculatePsk(totalAmount, rate));
            credit.setIsInsuranceEnabled(scoringDataDto.getIsInsuranceEnabled());
            credit.setIsSalaryClient(scoringDataDto.getIsSalaryClient());
            credit.setPaymentSchedule(scheduleService.getPaymentSchedule(totalAmount, monthlyPayment,
                    rate, scoringDataDto.getTerm()));
        }
        return credit;
    }

    private boolean preScoring(LoanStatementRequestDto loanStatementRequestDto) {
        return validationService.isValidRequest(loanStatementRequestDto);
    }

    private boolean scoring(ScoringDataDto scoringDataDto) {
        return true;
    }

    private LoanOfferDto calculateOffer(LoanStatementRequestDto loanStatementRequestDto,
                                        boolean isInsuranceEnabled, boolean isSalaryClient) {
        UUID uuid = UUID.randomUUID();
        var requestedAmount = loanStatementRequestDto.getAmount();
        var term = loanStatementRequestDto.getTerm();
        var totalAmount = calculateTotalAmount(requestedAmount, term, isInsuranceEnabled);
        var rate = calculatePreliminaryRate(isInsuranceEnabled, isSalaryClient);
        var monthlyPayment = calculateMonthlyPayment(totalAmount, rate, term);

        return new LoanOfferDto(
                uuid,
                requestedAmount,
                totalAmount,
                term,
                monthlyPayment,
                rate,
                isInsuranceEnabled,
                isSalaryClient
        );
    }

    private BigDecimal calculateTotalAmount(BigDecimal amount, Integer term, boolean isInsuranceEnabled) {
        var totalAmount = amount;
        if (isInsuranceEnabled) {
            var insuranceCost = BigDecimal.valueOf(10000).
                            add((amount.divide(BigDecimal.valueOf(1000), 2, RoundingMode.HALF_UP)).
                            multiply(BigDecimal.valueOf(term))
            );
            totalAmount = amount.add(insuranceCost);
        }
        return totalAmount;
    }

    private BigDecimal calculatePreliminaryRate(boolean isInsuranceEnabled, boolean isSalaryClient) {
        BigDecimal rate = BASE_RATE;
        if (isInsuranceEnabled) rate = rate.subtract(BigDecimal.valueOf(5));
        if (isSalaryClient) rate = rate.subtract(BigDecimal.valueOf(1));
        return rate;
    }

    private BigDecimal calculateMonthlyPayment(BigDecimal amount, BigDecimal rate, Integer term) {
        var monthlyRate = rate.divide(BigDecimal.valueOf(1200), 5, RoundingMode.HALF_UP);
        var annuityCoefficient = (monthlyRate.multiply((monthlyRate.add(BigDecimal.ONE)).pow(term))).divide(
                ((monthlyRate.add(BigDecimal.ONE)).pow(term)).subtract(BigDecimal.ONE), 10, RoundingMode.HALF_UP);
        var monthlyPayment = amount.multiply(annuityCoefficient).setScale(2, RoundingMode.HALF_UP);
        return monthlyPayment;
    }

    private BigDecimal calculatePsk(BigDecimal totalAmount, BigDecimal rate) {
        return totalAmount.add(
                (totalAmount.multiply(rate.divide(BigDecimal.valueOf(100), 5, RoundingMode.HALF_UP)))
        ).setScale(2, RoundingMode.HALF_UP);
    }
}
