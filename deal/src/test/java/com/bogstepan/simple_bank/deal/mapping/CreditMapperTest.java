package com.bogstepan.simple_bank.deal.mapping;

import com.bogstepan.simple_bank.calculator_client.dto.CreditDto;
import com.bogstepan.simple_bank.calculator_client.dto.PaymentScheduleElementDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CreditMapperTest {

    private final CreditMapper mapper = new CreditMapperImpl();

    @Test
    public void shouldProperlyMapCreditDtoToCredit() {
        var creditDto = new CreditDto(
                new BigDecimal("200000"),
                24,
                new BigDecimal("20000"),
                new BigDecimal("10"),
                new BigDecimal("220000"),
                true,
                false,
                List.of(
                        new PaymentScheduleElementDto(
                                1,
                                LocalDate.now(),
                                new BigDecimal("20000"),
                                new BigDecimal("2000"),
                                new BigDecimal("18000"),
                                new BigDecimal("200000")
                        )
                )
        );

        var credit = mapper.toCredit(creditDto);

        assertThat(credit).isNotNull();
        assertThat(credit.getAmount()).isEqualTo(creditDto.getAmount());
        assertThat(credit.getTerm()).isEqualTo(creditDto.getTerm());
        assertThat(credit.getMonthlyPayment()).isEqualTo(creditDto.getMonthlyPayment());
        assertThat(credit.getRate()).isEqualTo(creditDto.getRate());
        assertThat(credit.getPsk()).isEqualTo(creditDto.getPsk());
        assertThat(credit.getInsuranceEnabled()).isEqualTo(creditDto.getIsInsuranceEnabled());
        assertThat(credit.getSalaryClient()).isEqualTo(creditDto.getIsSalaryClient());
        assertThat(credit.getPaymentSchedule().getPaymentSchedule()).isEqualTo(creditDto.getPaymentSchedule());
    }

}