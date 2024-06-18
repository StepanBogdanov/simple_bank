package com.bogstepan.simple_bank.deal.mapping;

import com.bogstepan.simple_bank.deal.model.dto.ScoringDataDto;
import com.bogstepan.simple_bank.deal.model.entity.Client;
import com.bogstepan.simple_bank.deal.model.entity.Statement;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = EmploymentMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ScoringDataMapper {

    @Mapping(source = "statement.appliedOffer.totalAmount", target = "amount")
    @Mapping(source = "statement.appliedOffer.term", target = "term")
    @Mapping(source = "statement.appliedOffer.isInsuranceEnabled", target = "isInsuranceEnabled")
    @Mapping(source = "statement.appliedOffer.isSalaryClient", target = "isSalaryClient")
    @Mapping(source = "client.passport.series", target = "passportSeries")
    @Mapping(source = "client.passport.number", target = "passportNumber")
    @Mapping(source = "client.passport.issueBranch", target = "passportIssueBranch")
    @Mapping(source = "client.passport.issueDate", target = "passportIssueDate")
    @Mapping(source = "client.accountNumber", target = "account")
    ScoringDataDto toScoringDataDto(Statement statement, Client client);
}
