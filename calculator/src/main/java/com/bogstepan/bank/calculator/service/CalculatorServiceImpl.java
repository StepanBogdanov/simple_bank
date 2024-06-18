package com.bogstepan.bank.calculator.service;

import com.bogstepan.bank.calculator.dto.CreditDto;
import com.bogstepan.bank.calculator.dto.LoanOfferDto;
import com.bogstepan.bank.calculator.dto.LoanStatementRequestDto;
import com.bogstepan.bank.calculator.dto.ScoringDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculatorServiceImpl implements CalculatorService {

    private final ScheduleService scheduleService;
    private final ValidationService validationService;
    private final RateService rateService;

    @Override
    public List<LoanOfferDto> calculateOffers(LoanStatementRequestDto loanStatementRequestDto) {
        List<LoanOfferDto> offers = new ArrayList<>();
        offers.add(calculateOffer(loanStatementRequestDto, false, false));
        offers.add(calculateOffer(loanStatementRequestDto, false, true));
        offers.add(calculateOffer(loanStatementRequestDto, true, false));
        offers.add(calculateOffer(loanStatementRequestDto, true, true));
        return offers.stream()
                .sorted(Comparator.comparing(LoanOfferDto::getRate, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CreditDto> calculateCredit(ScoringDataDto scoringDataDto) {
        if (validationService.scoring(scoringDataDto)) {
            var rate = rateService.calculateFinalRate(scoringDataDto);
            var totalAmount = calculateTotalAmount(scoringDataDto.getAmount(), scoringDataDto.getTerm(),
                    scoringDataDto.getIsInsuranceEnabled());
            var monthlyPayment = calculateMonthlyPayment(totalAmount, rate, scoringDataDto.getTerm());
            return Optional.of(CreditDto.builder()
                    .amount(totalAmount)
                    .term(scoringDataDto.getTerm())
                    .monthlyPayment(monthlyPayment)
                    .rate(rate)
                    .psk(calculatePsk(totalAmount, rate))
                    .isInsuranceEnabled(scoringDataDto.getIsInsuranceEnabled())
                    .isSalaryClient(scoringDataDto.getIsSalaryClient())
                    .paymentSchedule(scheduleService.getPaymentSchedule(totalAmount, monthlyPayment,
                    rate, scoringDataDto.getTerm()))
                    .build());
        }
        return Optional.empty();
    }

    private LoanOfferDto calculateOffer(LoanStatementRequestDto loanStatementRequestDto,
                                        boolean isInsuranceEnabled, boolean isSalaryClient) {
        UUID uuid = null;
        var requestedAmount = loanStatementRequestDto.getAmount();
        var term = loanStatementRequestDto.getTerm();
        var totalAmount = calculateTotalAmount(requestedAmount, term, isInsuranceEnabled);
        var rate = rateService.calculatePreliminaryRate(isInsuranceEnabled, isSalaryClient);
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

    private BigDecimal calculateMonthlyPayment(BigDecimal amount, BigDecimal rate, Integer term) {
        var monthlyRate = rate.divide(BigDecimal.valueOf(1200), 5, RoundingMode.HALF_UP);
        var annuityCoefficient = (monthlyRate.multiply((monthlyRate.add(BigDecimal.ONE)).pow(term))).divide(
                ((monthlyRate.add(BigDecimal.ONE)).pow(term)).subtract(BigDecimal.ONE), 10, RoundingMode.HALF_UP);
        return amount.multiply(annuityCoefficient).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculatePsk(BigDecimal totalAmount, BigDecimal rate) {
        return totalAmount.add(
                (totalAmount.multiply(rate.divide(BigDecimal.valueOf(100), 5, RoundingMode.HALF_UP)))
        ).setScale(2, RoundingMode.HALF_UP);
    }
}