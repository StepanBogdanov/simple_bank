package com.bogstepan.simple_bank.deal.service;

import com.bogstepan.simple_bank.deal.model.dto.FinishRegistrationRequestDto;
import com.bogstepan.simple_bank.deal.model.dto.LoanStatementRequestDto;
import com.bogstepan.simple_bank.deal.model.entity.Client;
import com.bogstepan.simple_bank.deal.model.json.Employment;
import com.bogstepan.simple_bank.deal.model.json.Passport;
import com.bogstepan.simple_bank.deal.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Client saveNewClient(LoanStatementRequestDto loanStatementRequestDto) {
        return clientRepository.save(Client.builder()
                .firstName(loanStatementRequestDto.getFirstName())
                .lastName(loanStatementRequestDto.getLastName())
                .middleName(loanStatementRequestDto.getMiddleName())
                .birthDate(loanStatementRequestDto.getBirthDate())
                .email(loanStatementRequestDto.getEmail())
                .passport(new Passport(
                        loanStatementRequestDto.getPassportSeries(),
                        loanStatementRequestDto.getPassportNumber()
                ))
                .build());
    }

    public Client updateClient(FinishRegistrationRequestDto finishRegistrationRequestDto, Client client) {
        client.setGender(finishRegistrationRequestDto.getGender());
        client.setMaritalStatus(finishRegistrationRequestDto.getMaritalStatus());
        client.setDependentAmount(finishRegistrationRequestDto.getDependentAmount());
        client.getPassport().setIssueDate(finishRegistrationRequestDto.getPassportIssueDate());
        client.getPassport().setIssueBranch(finishRegistrationRequestDto.getPassportIssueBranch());
        client.setEmployment(Employment.builder()
                        .status(finishRegistrationRequestDto.getEmployment().getEmploymentStatus())
                        .employerInn(finishRegistrationRequestDto.getEmployment().getEmployerINN())
                        .salary(finishRegistrationRequestDto.getEmployment().getSalary())
                        .position(finishRegistrationRequestDto.getEmployment().getPosition())
                        .workExperienceTotal(finishRegistrationRequestDto.getEmployment().getWorkExperienceTotal())
                        .workExperienceCurrent(finishRegistrationRequestDto.getEmployment().getWorkExperienceCurrent())
                        .build());
        client.setAccountNumber(finishRegistrationRequestDto.getAccountNumber());
        return clientRepository.save(client);
    }
}
