package com.bogstepan.simple_bank.dossier.service;

import com.bogstepan.simple_bank.clients.dto.*;
import com.bogstepan.simple_bank.clients.enums.Gender;
import com.bogstepan.simple_bank.clients.enums.MaritalStatus;
import com.bogstepan.simple_bank.dossier.exception.RequestException;
import com.bogstepan.simple_bank.dossier.feign.DealFeignClient;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class LoanDocumentsServiceTest {

    @Mock
    DealFeignClient dealFeignClient;

    @InjectMocks
    LoanDocumentsService loanDocumentsService;

    private final StatementDto statement = new StatementDto(
            new ClientDto(
                "Ivan",
                "Ivanov",
                "Ivanovich",
                LocalDate.of(2000, 1, 1),
                "mail@mail.ru",
                Gender.MALE,
                MaritalStatus.MARRIED,
                1,
                new PassportDto("1111", "123456", "branch", LocalDate.of(2020, 1, 1)),
                new EmploymentDto(),
                "999999999"),
            new CreditDto(
                BigDecimal.valueOf(2199999.99),
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
                        BigDecimal.valueOf(210000)))),
          LocalDateTime.now(),
          new LoanOfferDto(),
          null,
          null
    );

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
        String statementId = "test";
        Mockito.when(dealFeignClient.getStatement(statementId)).thenReturn(statement);

        File file = loanDocumentsService.createLoanDocuments(statementId);

        Mockito.verify(dealFeignClient, times(1)).getStatement(statementId);
        assertTrue(file.exists());
        try (InputStream inputStream = new FileInputStream(file)) {
            XWPFDocument document = new XWPFDocument(inputStream);
            assertThat(document.getParagraphArray(0).getText())
                    .contains("КРЕДИТНЫЙ ДОГОВОР № " + statementId);
            assertThat(document.getParagraphArray(2).getText())
                    .contains(loanDocumentsService.transliterate(statement.getClient().getFirstName()));
            assertThat(document.getParagraphArray(2).getText())
                    .contains(loanDocumentsService.transliterate(statement.getClient().getLastName()));
            assertThat(document.getParagraphArray(2).getText())
                    .contains(loanDocumentsService.transliterate(statement.getClient().getMiddleName()));
            assertThat(document.getParagraphArray(4).getText())
                    .contains(String.format("%,.2f", statement.getCredit().getAmount().doubleValue()));
            assertThat(document.getParagraphArray(4).getText())
                    .contains(statement.getCredit().getTerm().toString());
            assertThat(document.getParagraphArray(6).getText())
                    .contains(statement.getClient().getAccountNumber());
            assertThat(document.getParagraphArray(7).getText())
                    .contains(statement.getCredit().getRate().toString());
            assertThat(document.getTableArray(0).getText())
                    .contains(statement.getCreationDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            assertThat(document.getTableArray(1).getText())
                    .contains(loanDocumentsService.transliterate(statement.getClient().getFirstName()));
            assertThat(document.getTableArray(1).getText())
                    .contains(loanDocumentsService.transliterate(statement.getClient().getLastName()));
            assertThat(document.getTableArray(1).getText())
                    .contains(loanDocumentsService.transliterate(statement.getClient().getMiddleName()));
            assertThat(document.getTableArray(1).getText())
                    .contains(statement.getClient().getPassport().getSeries());
            assertThat(document.getTableArray(1).getText())
                    .contains(statement.getClient().getPassport().getNumber());
            assertThat(document.getTableArray(1).getText())
                    .contains(statement.getClient().getPassport().getIssueDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            assertThat(document.getTableArray(1).getText())
                    .contains(loanDocumentsService.transliterate(statement.getClient().getPassport().getIssueBranch()));
            assertThat(document.getTableArray(2).getText())
                    .contains(statement.getCredit().getPaymentSchedule().get(0).getDate().toString());
            assertThat(document.getTableArray(2).getText())
                    .contains(String.format("%,.0f", statement.getCredit().getPaymentSchedule().get(0).getTotalPayment().doubleValue()));
            assertThat(document.getTableArray(2).getText())
                    .contains(String.format("%,.0f", statement.getCredit().getPaymentSchedule().get(0).getRemainingDebt().doubleValue()));
        } catch (IOException e) {
            throw new RequestException(e.getMessage());
        }
    }
}