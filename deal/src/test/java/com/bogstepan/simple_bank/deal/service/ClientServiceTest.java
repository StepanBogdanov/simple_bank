package com.bogstepan.simple_bank.deal.service;

import com.bogstepan.simple_bank.calculator_client.dto.FinishRegistrationRequestDto;
import com.bogstepan.simple_bank.calculator_client.dto.LoanStatementRequestDto;
import com.bogstepan.simple_bank.deal.mapping.ClientMapper;
import com.bogstepan.simple_bank.deal.model.entity.Client;
import com.bogstepan.simple_bank.deal.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    ClientRepository clientRepository;
    @Mock
    ClientMapper clientMapper;

    @InjectMocks
    ClientService clientService;

    @Test
    public void saveClient() {
        var request = new LoanStatementRequestDto();
        var client = new Client();
        Mockito.when(clientMapper.newClient(request)).thenReturn(client);
        Mockito.when(clientRepository.save(client)).thenReturn(client);
        clientService.saveNewClient(request);
        Mockito.verify(clientMapper, times(1)).newClient(request);
        Mockito.verify(clientRepository, times(1)).save(client);
    }

    @Test
    public void updateClient() {
        var request = new FinishRegistrationRequestDto();
        var client = new Client();
        Mockito.when(clientMapper.updateClient(client, request)).thenReturn(client);
        Mockito.when(clientRepository.save(client)).thenReturn(client);
        clientService.updateClient(client, request);
        Mockito.verify(clientMapper, times(1)).updateClient(client, request);
        Mockito.verify(clientRepository, times(1)).save(client);
    }

}