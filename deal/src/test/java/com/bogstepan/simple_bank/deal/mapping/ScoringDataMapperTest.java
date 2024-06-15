package com.bogstepan.simple_bank.deal.mapping;

import com.bogstepan.simple_bank.deal.model.dto.LoanOfferDto;
import com.bogstepan.simple_bank.deal.model.dto.ScoringDataDto;
import com.bogstepan.simple_bank.deal.model.entity.Client;
import com.bogstepan.simple_bank.deal.model.entity.Statement;
import com.bogstepan.simple_bank.deal.model.enums.*;
import com.bogstepan.simple_bank.deal.model.json.Employment;
import com.bogstepan.simple_bank.deal.model.json.Passport;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;


class ScoringDataMapperTest {

    private final ScoringDataMapper mapper = new ScoringDataMapperImpl(new EmploymentMapperImpl());

    @Test
    public void shouldProperlyMapStatementAndClientToScoringDataDto() {
        Client client = new Client(
                UUID.randomUUID(),
                "firstName",
                "lastName",
                "middleName",
                LocalDate.of(2000, 1, 1),
                "name@mail.ru",
                Gender.MALE,
                MaritalStatus.MARRIED,
                2,
                new Passport(
                        "1111",
                        "123456",
                        "branch",
                        LocalDate.of(2015, 1, 1)
                ),
                new Employment(
                        EmploymentStatus.SELF_EMPLOYED,
                        "1234567890",
                        new BigDecimal("50000"),
                        EmploymentPosition.WORKER,
                        36,
                        12
                ),
                "12345678901234567890"
        );

        Statement statement = new Statement(
                UUID.randomUUID(),
                client,
                null,
                ApplicationStatus.APPROVED,
                LocalDateTime.now(),
                new LoanOfferDto(
                        UUID.randomUUID(),
                        new BigDecimal("200000"),
                        new BigDecimal("200000"),
                        24,
                        new BigDecimal("10000"),
                        new BigDecimal("10"),
                        false,
                        true
                ),
                null,
                null,
                null
        );

        ScoringDataDto scoringDataDto = mapper.toScoringDataDto(statement, client);

        assertThat(scoringDataDto).isNotNull();
        assertThat(scoringDataDto.getAmount()).isEqualTo(statement.getAppliedOffer().getTotalAmount());
        assertThat(scoringDataDto.getTerm()).isEqualTo(statement.getAppliedOffer().getTerm());
        assertThat(scoringDataDto.getFirstName()).isEqualTo(client.getFirstName());
        assertThat(scoringDataDto.getLastName()).isEqualTo(client.getLastName());
        assertThat(scoringDataDto.getMiddleName()).isEqualTo(client.getMiddleName());
        assertThat(scoringDataDto.getGender()).isEqualTo(client.getGender());
        assertThat(scoringDataDto.getBirthDate()).isEqualTo(client.getBirthDate());
        assertThat(scoringDataDto.getPassportSeries()).isEqualTo(client.getPassport().getSeries());
        assertThat(scoringDataDto.getPassportNumber()).isEqualTo(client.getPassport().getNumber());
        assertThat(scoringDataDto.getPassportIssueBranch()).isEqualTo(client.getPassport().getIssueBranch());
        assertThat(scoringDataDto.getPassportIssueDate()).isEqualTo(client.getPassport().getIssueDate());
        assertThat(scoringDataDto.getMaritalStatus()).isEqualTo(client.getMaritalStatus());
        assertThat(scoringDataDto.getDependentAmount()).isEqualTo(client.getDependentAmount());
        assertThat(scoringDataDto.getEmployment().getEmploymentStatus()).isEqualTo(client.getEmployment().getStatus());
        assertThat(scoringDataDto.getEmployment().getEmployerINN()).isEqualTo(client.getEmployment().getEmployerINN());
        assertThat(scoringDataDto.getEmployment().getSalary()).isEqualTo(client.getEmployment().getSalary());
        assertThat(scoringDataDto.getEmployment().getPosition()).isEqualTo(client.getEmployment().getPosition());
        assertThat(scoringDataDto.getEmployment().getWorkExperienceTotal()).isEqualTo(client.getEmployment().getWorkExperienceTotal());
        assertThat(scoringDataDto.getEmployment().getWorkExperienceCurrent()).isEqualTo(client.getEmployment().getWorkExperienceCurrent());
        assertThat(scoringDataDto.getAccount()).isEqualTo(client.getAccountNumber());
        assertThat(scoringDataDto.getIsInsuranceEnabled()).isEqualTo(statement.getAppliedOffer().getIsInsuranceEnabled());
        assertThat(scoringDataDto.getIsSalaryClient()).isEqualTo(statement.getAppliedOffer().getIsSalaryClient());
    }

}