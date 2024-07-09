package com.bogstepan.simple_bank.deal.mapping;

import com.bogstepan.simple_bank.calculator_client.dto.ClientDto;
import com.bogstepan.simple_bank.calculator_client.dto.FinishRegistrationRequestDto;
import com.bogstepan.simple_bank.calculator_client.dto.LoanStatementRequestDto;
import com.bogstepan.simple_bank.deal.model.entity.Client;
import org.mapstruct.*;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {EmploymentMapper.class, PassportMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ClientMapper {

    @Mapping(source = "loanStatementRequestDto.passportSeries", target = "passport.series")
    @Mapping(source = "loanStatementRequestDto.passportNumber", target = "passport.number")
    Client newClient(LoanStatementRequestDto loanStatementRequestDto);

    @Mapping(source = "finishRegistrationRequestDto.passportIssueDate", target = "passport.issueDate")
    @Mapping(source = "finishRegistrationRequestDto.passportIssueBranch", target = "passport.issueBranch")
    Client updateClient(@MappingTarget Client client, FinishRegistrationRequestDto finishRegistrationRequestDto);

    Client toClient(ClientDto clientDto);

    ClientDto toClientDto(Client client);
}
