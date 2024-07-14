package com.bogstepan.simple_bank.deal.service;

import com.bogstepan.simple_bank.clients.dto.CreditDto;
import com.bogstepan.simple_bank.deal.mapping.CreditMapper;
import com.bogstepan.simple_bank.deal.model.entity.Credit;
import com.bogstepan.simple_bank.deal.repository.CreditRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CreditServiceTest {

    @Mock
    CreditRepository creditRepository;
    @Mock
    CreditMapper creditMapper;

    @InjectMocks
    CreditService creditService;

    @Test
    public void saveCredit() {
        var creditDto = new CreditDto();
        var credit = new Credit();
        Mockito.when(creditMapper.toCredit(creditDto)).thenReturn(credit);
        Mockito.when(creditRepository.save(credit)).thenReturn(credit);
        creditService.saveCredit(creditDto);
        Mockito.verify(creditMapper, times(1)).toCredit(creditDto);
        Mockito.verify(creditRepository, times(1)).save(credit);
    }

}