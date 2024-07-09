package com.bogstepan.simple_bank.deal.mapping;

import com.bogstepan.simple_bank.calculator_client.dto.PassportDto;
import com.bogstepan.simple_bank.deal.model.json.Passport;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PassportMapper {

    Passport toPassport(PassportDto passportDto);

    PassportDto toPassportDto(Passport passport);
}
