package com.bogstepan.bank.calculator.service;

import com.bogstepan.bank.calculator.dto.PaymentScheduleElementDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ScheduleService {
    public List<PaymentScheduleElementDto> getPaymentSchedule(BigDecimal totalAmount, BigDecimal monthlyPayment,
                                                              BigDecimal rate, Integer term) {
        return List.of();
    }
}
