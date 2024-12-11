package com.bogstepan.bank.calculator.service;

import com.bogstepan.simple_bank.clients.dto.PaymentScheduleElementDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {
    public List<PaymentScheduleElementDto> getPaymentSchedule(BigDecimal totalAmount, BigDecimal monthlyPayment,
                                                              BigDecimal rate, int term) {
        List<PaymentScheduleElementDto> paymentSchedule = new ArrayList<>();
        int number = 0;
        BigDecimal remainingDebt = totalAmount;
        LocalDate currentDate = LocalDate.now();
        while (number < term) {
            number++;
            LocalDate nextPaymentDate = currentDate.plusMonths(1);
            var numberOfDaysInMonth = ChronoUnit.DAYS.between(currentDate, nextPaymentDate);
            var numberOfDaysInYear = currentDate.lengthOfYear();
            var interestPayment = remainingDebt.
                    multiply(rate.divide(BigDecimal.valueOf(100), 5, RoundingMode.HALF_UP)).
                    multiply(BigDecimal.valueOf(numberOfDaysInMonth)).
                    divide(BigDecimal.valueOf(numberOfDaysInYear), 2, RoundingMode.HALF_UP);
            var debtPayment = monthlyPayment.subtract(interestPayment);
            if (number == term) {
                debtPayment = remainingDebt;
                monthlyPayment = debtPayment.add(interestPayment);
            }
            remainingDebt = remainingDebt.subtract(debtPayment);
            currentDate = nextPaymentDate;
            paymentSchedule.add(new PaymentScheduleElementDto(
                    number,
                    nextPaymentDate,
                    monthlyPayment,
                    interestPayment,
                    debtPayment,
                    remainingDebt
            ));
        }
        return paymentSchedule;
    }
}
