package com.bogstepan.simple_bank.deal.mapping;

import com.bogstepan.simple_bank.clients.dto.*;
import com.bogstepan.simple_bank.clients.enums.EmploymentPosition;
import com.bogstepan.simple_bank.clients.enums.EmploymentStatus;
import com.bogstepan.simple_bank.clients.enums.Gender;
import com.bogstepan.simple_bank.clients.enums.MaritalStatus;
import com.bogstepan.simple_bank.deal.model.entity.Client;
import com.bogstepan.simple_bank.deal.model.json.Employment;
import com.bogstepan.simple_bank.deal.model.json.Passport;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ClientMapperTest {

    private final ClientMapper mapper = new ClientMapperImpl(new EmploymentMapperImpl(), new PassportMapperImpl());

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

    @Test
    public void shouldProperlyMapClientToUpdateClientDto() {
        Client client = new Client(
                null,
                "firstName",
                "lastName",
                "middleName",
                LocalDate.of(2000, 1, 1),
                "name@mail.ru",
                Gender.MALE,
                MaritalStatus.MARRIED,
                1,
                new Passport(
                        "1111",
                        "123456",
                        "branch",
                        LocalDate.of(2000, 1, 1)
                ),
                new Employment(
                        EmploymentStatus.SELF_EMPLOYED,
                        "1234567890",
                        new BigDecimal("50000"),
                        EmploymentPosition.WORKER,
                        36,
                        12
                ),
                "123456789"
        );
        ClientDto clientDto = mapper.toClientDto(client);

        assertThat(clientDto).isNotNull();
        assertThat(clientDto.getFirstName()).isEqualTo(client.getFirstName());
        assertThat(clientDto.getLastName()).isEqualTo(client.getLastName());
        assertThat(clientDto.getMiddleName()).isEqualTo(client.getMiddleName());
        assertThat(clientDto.getBirthDate()).isEqualTo(client.getBirthDate());
        assertThat(clientDto.getEmail()).isEqualTo(client.getEmail());
        assertThat(clientDto.getGender()).isEqualTo(client.getGender());
        assertThat(clientDto.getMaritalStatus()).isEqualTo(client.getMaritalStatus());
        assertThat(clientDto.getDependentAmount()).isEqualTo(client.getDependentAmount());
        assertThat(clientDto.getPassport().getSeries()).isEqualTo(client.getPassport().getSeries());
        assertThat(clientDto.getPassport().getNumber()).isEqualTo(client.getPassport().getNumber());
        assertThat(clientDto.getPassport().getIssueBranch()).isEqualTo(client.getPassport().getIssueBranch());
        assertThat(clientDto.getPassport().getIssueDate()).isEqualTo(client.getPassport().getIssueDate());
        assertThat(clientDto.getEmployment().getEmploymentStatus()).isEqualTo(client.getEmployment().getStatus());
        assertThat(clientDto.getEmployment().getEmployerINN()).isEqualTo(client.getEmployment().getEmployerINN());
        assertThat(clientDto.getEmployment().getSalary()).isEqualTo(client.getEmployment().getSalary());
        assertThat(clientDto.getEmployment().getPosition()).isEqualTo(client.getEmployment().getPosition());
        assertThat(clientDto.getEmployment().getWorkExperienceTotal()).isEqualTo(client.getEmployment().getWorkExperienceTotal());
        assertThat(clientDto.getEmployment().getWorkExperienceCurrent()).isEqualTo(client.getEmployment().getWorkExperienceCurrent());
        assertThat(clientDto.getAccountNumber()).isEqualTo(client.getAccountNumber());
    }

    @Test
    public void shouldProperlyMapClientDtoToUpdateClient() {
        ClientDto clientDto = new ClientDto(
                "firstName",
                "lastName",
                "middleName",
                LocalDate.of(2000, 1, 1),
                "name@mail.ru",
                Gender.MALE,
                MaritalStatus.MARRIED,
                1,
                new PassportDto(
                        "1111",
                        "123456",
                        "branch",
                        LocalDate.of(2000, 1, 1)
                ),
                new EmploymentDto(
                        EmploymentStatus.SELF_EMPLOYED,
                        "1234567890",
                        new BigDecimal("50000"),
                        EmploymentPosition.WORKER,
                        36,
                        12
                ),
                "123456789"
        );
        Client client = mapper.toClient(clientDto);

        assertThat(client).isNotNull();
        assertThat(clientDto.getFirstName()).isEqualTo(client.getFirstName());
        assertThat(clientDto.getLastName()).isEqualTo(client.getLastName());
        assertThat(clientDto.getMiddleName()).isEqualTo(client.getMiddleName());
        assertThat(clientDto.getBirthDate()).isEqualTo(client.getBirthDate());
        assertThat(clientDto.getEmail()).isEqualTo(client.getEmail());
        assertThat(clientDto.getGender()).isEqualTo(client.getGender());
        assertThat(clientDto.getMaritalStatus()).isEqualTo(client.getMaritalStatus());
        assertThat(clientDto.getDependentAmount()).isEqualTo(client.getDependentAmount());
        assertThat(clientDto.getPassport().getSeries()).isEqualTo(client.getPassport().getSeries());
        assertThat(clientDto.getPassport().getNumber()).isEqualTo(client.getPassport().getNumber());
        assertThat(clientDto.getPassport().getIssueBranch()).isEqualTo(client.getPassport().getIssueBranch());
        assertThat(clientDto.getPassport().getIssueDate()).isEqualTo(client.getPassport().getIssueDate());
        assertThat(clientDto.getEmployment().getEmploymentStatus()).isEqualTo(client.getEmployment().getStatus());
        assertThat(clientDto.getEmployment().getEmployerINN()).isEqualTo(client.getEmployment().getEmployerINN());
        assertThat(clientDto.getEmployment().getSalary()).isEqualTo(client.getEmployment().getSalary());
        assertThat(clientDto.getEmployment().getPosition()).isEqualTo(client.getEmployment().getPosition());
        assertThat(clientDto.getEmployment().getWorkExperienceTotal()).isEqualTo(client.getEmployment().getWorkExperienceTotal());
        assertThat(clientDto.getEmployment().getWorkExperienceCurrent()).isEqualTo(client.getEmployment().getWorkExperienceCurrent());
        assertThat(clientDto.getAccountNumber()).isEqualTo(client.getAccountNumber());
    }

}