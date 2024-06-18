package com.bogstepan.simple_bank.deal.mapping;

import com.bogstepan.simple_bank.deal.model.dto.EmploymentDto;
import com.bogstepan.simple_bank.deal.model.dto.FinishRegistrationRequestDto;
import com.bogstepan.simple_bank.deal.model.dto.LoanStatementRequestDto;
import com.bogstepan.simple_bank.deal.model.entity.Client;
import com.bogstepan.simple_bank.deal.model.enums.EmploymentPosition;
import com.bogstepan.simple_bank.deal.model.enums.EmploymentStatus;
import com.bogstepan.simple_bank.deal.model.enums.Gender;
import com.bogstepan.simple_bank.deal.model.enums.MaritalStatus;
import com.bogstepan.simple_bank.deal.model.json.Passport;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class ClientMapperTest {

    private final ClientMapper mapper = new ClientMapperImpl(new EmploymentMapperImpl());

    @Test
    public void shouldProperlyMapLoanStatementRequestDtoToNewClient() {
        LoanStatementRequestDto loanStatementRequestDto = new LoanStatementRequestDto(
                new BigDecimal("200000"),
                24,
                "firstName",
                "lastName",
                "middleName",
                "name@mail.ru",
                LocalDate.of(2000, 1, 1),
                "1111",
                "123456"
        );

        Client client = mapper.newClient(loanStatementRequestDto);

        assertThat(client).isNotNull();
        assertThat(client.getFirstName()).isEqualTo(loanStatementRequestDto.getFirstName());
        assertThat(client.getLastName()).isEqualTo(loanStatementRequestDto.getLastName());
        assertThat(client.getMiddleName()).isEqualTo(loanStatementRequestDto.getMiddleName());
        assertThat(client.getBirthDate()).isEqualTo(loanStatementRequestDto.getBirthDate());
        assertThat(client.getEmail()).isEqualTo(loanStatementRequestDto.getEmail());
        assertThat(client.getPassport().getSeries()).isEqualTo(loanStatementRequestDto.getPassportSeries());
        assertThat(client.getPassport().getNumber()).isEqualTo(loanStatementRequestDto.getPassportNumber());
    }

    @Test
    public void shouldProperlyMapFinishRegistrationUpdateDtoToUpdateClient() {
        Client oldClient = new Client(
                null,
                "firstName",
                "lastName",
                "middleName",
                LocalDate.of(2000, 1,1),
                "name@mail.ru",
                null,
                null,
                null,
                new Passport(
                        "1111",
                        "123456",
                        null,
                        null
                ),
                null,
                null

        );
        FinishRegistrationRequestDto finishRegistrationRequestDto = new FinishRegistrationRequestDto(
                Gender.MALE,
                MaritalStatus.MARRIED,
                2,
                LocalDate.of(2015, 1, 1),
                "Branch",
                new EmploymentDto(
                        EmploymentStatus.SELF_EMPLOYED,
                        "1234567890",
                        new BigDecimal("50000"),
                        EmploymentPosition.WORKER,
                        36,
                        12
                ),
                "12345678901234567890"
        );

        Client updatedClient = mapper.updateClient(oldClient, finishRegistrationRequestDto);

        assertThat(updatedClient).isNotNull();
        assertThat(updatedClient.getFirstName()).isEqualTo(oldClient.getFirstName());
        assertThat(updatedClient.getLastName()).isEqualTo(oldClient.getLastName());
        assertThat(updatedClient.getMiddleName()).isEqualTo(oldClient.getMiddleName());
        assertThat(updatedClient.getBirthDate()).isEqualTo(oldClient.getBirthDate());
        assertThat(updatedClient.getEmail()).isEqualTo(oldClient.getEmail());
        assertThat(updatedClient.getGender()).isEqualTo(finishRegistrationRequestDto.getGender());
        assertThat(updatedClient.getMaritalStatus()).isEqualTo(finishRegistrationRequestDto.getMaritalStatus());
        assertThat(updatedClient.getDependentAmount()).isEqualTo(finishRegistrationRequestDto.getDependentAmount());
        assertThat(updatedClient.getPassport().getSeries()).isEqualTo(oldClient.getPassport().getSeries());
        assertThat(updatedClient.getPassport().getNumber()).isEqualTo(oldClient.getPassport().getNumber());
        assertThat(updatedClient.getPassport().getIssueBranch()).isEqualTo(finishRegistrationRequestDto.getPassportIssueBranch());
        assertThat(updatedClient.getPassport().getIssueDate()).isEqualTo(finishRegistrationRequestDto.getPassportIssueDate());
        assertThat(updatedClient.getEmployment().getStatus()).isEqualTo(finishRegistrationRequestDto.getEmployment().getEmploymentStatus());
        assertThat(updatedClient.getEmployment().getEmployerINN()).isEqualTo(finishRegistrationRequestDto.getEmployment().getEmployerINN());
        assertThat(updatedClient.getEmployment().getSalary()).isEqualTo(finishRegistrationRequestDto.getEmployment().getSalary());
        assertThat(updatedClient.getEmployment().getPosition()).isEqualTo(finishRegistrationRequestDto.getEmployment().getPosition());
        assertThat(updatedClient.getEmployment().getWorkExperienceTotal()).isEqualTo(finishRegistrationRequestDto.getEmployment().getWorkExperienceTotal());
        assertThat(updatedClient.getEmployment().getWorkExperienceCurrent()).isEqualTo(finishRegistrationRequestDto.getEmployment().getWorkExperienceCurrent());
        assertThat(updatedClient.getAccountNumber()).isEqualTo(finishRegistrationRequestDto.getAccountNumber());

    }

}