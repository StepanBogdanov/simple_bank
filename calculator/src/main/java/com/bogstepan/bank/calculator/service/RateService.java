package com.bogstepan.bank.calculator.service;

import com.bogstepan.simple_bank.clients.dto.ScoringDataDto;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@Getter
public class RateService {

    @Value("${rate.base_rate}")
    private BigDecimal baseRate;
    @Value("${rate.male_upper_age_limit}")
    private int maleUpperAgeLimit;
    @Value("${rate.male_lower_age_limit}")
    private int maleLowerAgeLimit;
    @Value("${rate.female_upper_age_limit}")
    private int femaleUpperAgeLimit;
    @Value("${rate.female_lower_age_limit}")
    private int femaleLowerAgeLimit;

    public BigDecimal calculatePreliminaryRate(boolean isInsuranceEnabled, boolean isSalaryClient) {
        BigDecimal rate = baseRate;
        if (isInsuranceEnabled) {
            rate = rate.subtract(BigDecimal.valueOf(5));
        }
        if (isSalaryClient) {
            rate = rate.subtract(BigDecimal.valueOf(1));
        }
        return rate;
    }

    public BigDecimal calculateFinalRate(ScoringDataDto data) {
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
                if (ChronoUnit.YEARS.between(data.getBirthDate(), LocalDate.now()) > maleLowerAgeLimit
                        && ChronoUnit.YEARS.between(data.getBirthDate(), LocalDate.now()) < maleUpperAgeLimit) {
                    rate = rate.subtract(BigDecimal.valueOf(3));
                }
            }
            case FEMALE -> {
                if (ChronoUnit.YEARS.between(data.getBirthDate(), LocalDate.now()) > femaleLowerAgeLimit
                        && ChronoUnit.YEARS.between(data.getBirthDate(), LocalDate.now()) < femaleUpperAgeLimit) {
                    rate = rate.subtract(BigDecimal.valueOf(3));
                }
            }
            case NON_BINARY -> rate = rate.add(BigDecimal.valueOf(7));
        }
        return rate;
    }
}
