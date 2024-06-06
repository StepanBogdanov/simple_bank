package com.bogstepan.simple_bank.deal.service;

import com.bogstepan.simple_bank.deal.model.dto.CreditDto;
import com.bogstepan.simple_bank.deal.model.entity.Credit;
import com.bogstepan.simple_bank.deal.model.enums.CreditStatus;
import com.bogstepan.simple_bank.deal.model.json.PaymentSchedule;
import com.bogstepan.simple_bank.deal.repository.CreditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditService {

    private final CreditRepository creditRepository;

    public Credit saveCredit(CreditDto creditDto) {
        return creditRepository.save(Credit.builder()
                        .amount(creditDto.getAmount())
                        .term(creditDto.getTerm())
                        .monthlyPayment(creditDto.getMonthlyPayment())
                        .rate(creditDto.getRate())
                        .psk(creditDto.getPsk())
                        .paymentSchedule(new PaymentSchedule(creditDto.getPaymentSchedule()))
                        .insuranceEnabled(creditDto.getIsInsuranceEnabled())
                        .salaryClient(creditDto.getIsSalaryClient())
                        .creditStatus(CreditStatus.CALCULATED)
                        .build());
    }
}
