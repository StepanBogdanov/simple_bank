package com.bogstepan.simple_bank.deal.service;

import com.bogstepan.simple_bank.deal.model.dto.LoanStatementRequestDto;
import com.bogstepan.simple_bank.deal.model.entity.Client;
import com.bogstepan.simple_bank.deal.model.json.Passport;
import com.bogstepan.simple_bank.deal.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Client saveClient(LoanStatementRequestDto loanStatementRequestDto) {
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
}
