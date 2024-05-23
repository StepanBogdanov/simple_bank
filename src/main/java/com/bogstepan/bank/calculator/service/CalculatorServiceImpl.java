package com.bogstepan.bank.calculator.service;

import com.bogstepan.bank.calculator.dto.CreditDto;
import com.bogstepan.bank.calculator.dto.LoanOfferDto;
import com.bogstepan.bank.calculator.dto.LoanStatementRequestDto;
import com.bogstepan.bank.calculator.dto.ScoringDataDto;
import com.bogstepan.bank.calculator.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
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
    public Optional<CreditDto> calculateCredit(ScoringDataDto scoringDataDto) {
        var rate = calculateFinalRate(scoringDataDto);
        if (scoring(scoringDataDto)) {
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

    private boolean preScoring(LoanStatementRequestDto loanStatementRequestDto) {
        return validationService.isValidRequest(loanStatementRequestDto);
    }

    private boolean scoring(ScoringDataDto scoringDataDto) {
        if (scoringDataDto.getEmployment().getEmploymentStatus().name().equals("UNEMPLOYED") ||
                scoringDataDto.getAmount().compareTo(scoringDataDto.getEmployment().getSalary().multiply(BigDecimal.valueOf(25))) > 0 ||
                ChronoUnit.YEARS.between(scoringDataDto.getBirthDate(), LocalDate.now()) < 20 ||
                ChronoUnit.YEARS.between(scoringDataDto.getBirthDate(),LocalDate.now()) > 65 ||
                scoringDataDto.getEmployment().getWorkExperienceTotal() < 18 ||
                scoringDataDto.getEmployment().getWorkExperienceCurrent() < 3) {
            log.warn("Credit denied");
            return false;
        }
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

    private BigDecimal calculateFinalRate(ScoringDataDto data) {
        var rate = calculatePreliminaryRate(data.getIsInsuranceEnabled(), data.getIsSalaryClient());
        switch (data.getEmployment().getEmploymentStatus()) {
            case SELF_EMPLOYED -> rate = rate.add(BigDecimal.ONE);
            case BUSINESS_OWNER -> rate = rate.add(BigDecimal.valueOf(2));
        }
        switch (data.getEmployment().getPosition()) {
            case MIDDLE_MANAGER -> rate = rate.subtract(BigDecimal.valueOf(2));
            case TOP_MANAGER -> rate = rate.subtract(BigDecimal.valueOf(3));
        }
        switch (data.getMaritalStatus()) {
            case MARRIED -> rate = rate.subtract(BigDecimal.valueOf(3));
            case DIVORCED -> rate = rate.add(BigDecimal.ONE);
        }
        switch (data.getGender()) {
            case MALE -> {
                if (ChronoUnit.YEARS.between(data.getBirthDate(), LocalDate.now()) > 30 &&
                        ChronoUnit.YEARS.between(data.getBirthDate(), LocalDate.now()) < 55) {
                    rate = rate.subtract(BigDecimal.valueOf(3));
                }
            }
            case FEMALE -> {
                if (ChronoUnit.YEARS.between(data.getBirthDate(), LocalDate.now()) > 32 &&
                        ChronoUnit.YEARS.between(data.getBirthDate(), LocalDate.now()) < 60) {
                    rate = rate.subtract(BigDecimal.valueOf(3));
                }
            }
            case NON_BINARY -> rate = rate.add(BigDecimal.valueOf(7));
        }
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