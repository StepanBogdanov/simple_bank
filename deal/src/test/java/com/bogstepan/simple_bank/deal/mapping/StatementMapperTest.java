package com.bogstepan.simple_bank.deal.mapping;

import com.bogstepan.simple_bank.clients.dto.LoanOfferDto;
import com.bogstepan.simple_bank.deal.model.entity.Client;
import com.bogstepan.simple_bank.deal.model.entity.Credit;
import com.bogstepan.simple_bank.deal.model.entity.Statement;
import com.bogstepan.simple_bank.deal.model.json.StatusHistory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class StatementMapperTest {

    private final StatementMapper statementMapper = new StatementMapperImpl(new CreditMapperImpl(), new ClientMapperImpl(
            new EmploymentMapperImpl(), new PassportMapperImpl()
    ));

    @Test
    public void shouldProperlyStatementToStatementDto() {
        var statement = new Statement(
                null,
                new Client(),
                new Credit(),
                null,
                LocalDateTime.now(),
                new LoanOfferDto(
                        UUID.randomUUID(),
                        BigDecimal.valueOf(200000),
                        BigDecimal.valueOf(210000),
                        12,
                        BigDecimal.valueOf(20000),
                        BigDecimal.valueOf(10),
                        true,
                        true
                ),
                LocalDateTime.now(),
                "123456",
                new StatusHistory()
        );

        var statementDto = statementMapper.toStatementDto(statement);

        assertThat(statementDto).isNotNull();
        assertThat(statementDto.getCreationDate()).isEqualTo(statement.getCreationDate());
        assertThat(statementDto.getAppliedOffer().getStatementId()).isEqualTo(statement.getAppliedOffer().getStatementId());
        assertThat(statementDto.getAppliedOffer().getRequestedAmount()).isEqualTo(statement.getAppliedOffer().getRequestedAmount());
        assertThat(statementDto.getAppliedOffer().getTotalAmount()).isEqualTo(statement.getAppliedOffer().getTotalAmount());
        assertThat(statementDto.getAppliedOffer().getTerm()).isEqualTo(statement.getAppliedOffer().getTerm());
        assertThat(statementDto.getAppliedOffer().getMonthlyPayment()).isEqualTo(statement.getAppliedOffer().getMonthlyPayment());
        assertThat(statementDto.getAppliedOffer().getRate()).isEqualTo(statement.getAppliedOffer().getRate());
        assertThat(statementDto.getAppliedOffer().getIsInsuranceEnabled()).isEqualTo(statement.getAppliedOffer().getIsInsuranceEnabled());
        assertThat(statementDto.getAppliedOffer().getIsSalaryClient()).isEqualTo(statement.getAppliedOffer().getIsSalaryClient());
        assertThat(statementDto.getSignDate()).isEqualTo(statement.getSignDate());
        assertThat(statementDto.getSesCode()).isEqualTo(statement.getSesCode());
    }
}