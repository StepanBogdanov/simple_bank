package com.bogstepan.simple_bank.deal.mapping;

import com.bogstepan.simple_bank.clients.dto.EmploymentDto;
import com.bogstepan.simple_bank.deal.model.json.Employment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmploymentMapper {

    @Mapping(source = "employmentStatus", target = "status")
    Employment toEmployment(EmploymentDto employmentDto);

    @Mapping(source = "status", target = "employmentStatus")
    EmploymentDto toEmploymentDto(Employment employment);
}
