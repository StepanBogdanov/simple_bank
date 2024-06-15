package com.bogstepan.simple_bank.deal.service;

import com.bogstepan.simple_bank.deal.mapping.ClientMapper;
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
    private final ClientMapper clientMapper;

    public Client saveNewClient(LoanStatementRequestDto loanStatementRequestDto) {
        return clientRepository.save(clientMapper.newClient(loanStatementRequestDto));
    }

    public Client updateClient(Client client, FinishRegistrationRequestDto finishRegistrationRequestDto) {
        return clientRepository.save(clientMapper.updateClient(client, finishRegistrationRequestDto));
    }
}
