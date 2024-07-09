package com.bogstepan.simple_bank.deal.mapping;

import com.bogstepan.simple_bank.calculator_client.dto.CreditDto;
import com.bogstepan.simple_bank.deal.model.entity.Credit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CreditMapper {

    @Mapping(source = "paymentSchedule", target = "paymentSchedule.paymentSchedule")
    @Mapping(source = "isInsuranceEnabled", target = "insuranceEnabled")
    @Mapping(source = "isSalaryClient", target = "salaryClient")
    Credit toCredit(CreditDto creditDto);

    @Mapping(source = "paymentSchedule.paymentSchedule", target = "paymentSchedule")
    @Mapping(source = "insuranceEnabled", target = "isInsuranceEnabled")
    @Mapping(source = "salaryClient", target = "isSalaryClient")
    CreditDto toCreditDto(Credit credit);
}
