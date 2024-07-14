package com.bogstepan.bank.calculator.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ScheduleServiceTest {

    @Autowired
    ScheduleService service;

    @Test
    public void scheduleTest() {
        var schedule = service.getPaymentSchedule(BigDecimal.valueOf(50000), BigDecimal.valueOf(5000),
                BigDecimal.valueOf(15), 12);
        assertThat(schedule.size()).isEqualTo(12);
        assertThat(schedule.get(11).getRemainingDebt().compareTo(BigDecimal.ZERO)).isEqualTo(0);
    }

}