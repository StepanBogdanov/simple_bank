package com.bogstepan.simple_bank.deal.service;

import com.bogstepan.simple_bank.deal.mapping.CreditMapper;
import com.bogstepan.simple_bank.deal.model.dto.CreditDto;
import com.bogstepan.simple_bank.deal.model.entity.Credit;
import com.bogstepan.simple_bank.deal.repository.CreditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditService {

    private final CreditRepository creditRepository;
    private final CreditMapper creditMapper;

    public Credit saveCredit(CreditDto creditDto) {
        return creditRepository.save(creditMapper.toCredit(creditDto));
    }
}
