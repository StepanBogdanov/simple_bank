package com.bogstepan.simple_bank.deal.mapping;

import com.bogstepan.simple_bank.calculator_client.dto.StatementDto;
import com.bogstepan.simple_bank.deal.model.entity.Statement;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CreditMapper.class, ClientMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface StatementMapper {

    Statement toStatement(StatementDto statementDto);

    StatementDto toStatementDto(Statement statement);
}
