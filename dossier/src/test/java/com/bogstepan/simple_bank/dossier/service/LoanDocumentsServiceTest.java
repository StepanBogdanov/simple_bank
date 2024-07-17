package com.bogstepan.simple_bank.dossier.service;

import com.bogstepan.simple_bank.clients.dto.*;
import com.bogstepan.simple_bank.clients.enums.Gender;
import com.bogstepan.simple_bank.clients.enums.MaritalStatus;
import com.bogstepan.simple_bank.dossier.feign.DealFeignClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class LoanDocumentsServiceTest {

    @Mock
    DealFeignClient dealFeignClient;

    @InjectMocks
    LoanDocumentsService loanDocumentsService;

    @Test
    public void shouldTransliterateTextCorrectly() {
        String in = "ivanov";
        String expected = "Иванов";
        assertThat(loanDocumentsService.transliterate(in)).isEqualTo(expected);
    }

    @Test
    public void shouldTransliterateEmptyTextCorrectly() {
        String in = "";
        assertThat(loanDocumentsService.transliterate(in)).isEmpty();
    }

    @Test
    public void createLoanDocument() {
        Mockito.when(dealFeignClient.getStatement("test")).thenReturn(new StatementDto(
                new ClientDto(
                        "Ivan",
                        "Ivanov",
                        "Ivanovich",
                        LocalDate.of(2000, 1, 1),
                        "mail@mail.ru",
                        Gender.MALE,
                        MaritalStatus.MARRIED,
                        1,
                        new PassportDto(
                                "1111",
                                "123456",
                                "branch",
                                LocalDate.of(2020, 1, 1)
                        ),
                        new EmploymentDto(),
                        "999999999"
                ),
                new CreditDto(
                        BigDecimal.valueOf(200000),
                        24,
                        BigDecimal.valueOf(10000),
                        BigDecimal.valueOf(10),
                        BigDecimal.valueOf(220000),
                        true,
                        true,
                        List.of(
                                new PaymentScheduleElementDto(
                                        1,
                                        LocalDate.now(),
                                        BigDecimal.valueOf(10000),
                                        BigDecimal.valueOf(2000),
                                        BigDecimal.valueOf(8000),
                                        BigDecimal.valueOf(210000)
                                )
                        )
                ),
                LocalDateTime.now(),
                new LoanOfferDto(),
                null,
                null
        ));

        loanDocumentsService.createLoanDocuments("test");

        Mockito.verify(dealFeignClient, times(1)).getStatement("test");
    }

}