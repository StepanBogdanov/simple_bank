package com.bogstepan.simple_bank.deal.mapping;

import com.bogstepan.simple_bank.calculator_client.dto.EmploymentDto;
import com.bogstepan.simple_bank.calculator_client.enums.EmploymentPosition;
import com.bogstepan.simple_bank.calculator_client.enums.EmploymentStatus;
import com.bogstepan.simple_bank.deal.model.json.Employment;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class EmploymentMapperTest {

    private final EmploymentMapper mapper = new EmploymentMapperImpl();

    @Test
    public void shouldProperlyMapEmploymentDtoToEmployment() {
        EmploymentDto employmentDto = new EmploymentDto(
                EmploymentStatus.SELF_EMPLOYED,
                "1234567890",
                new BigDecimal("50000"),
                EmploymentPosition.WORKER,
                36,
                12
        );

        Employment employment = mapper.toEmployment(employmentDto);

        assertThat(employment).isNotNull();
        assertThat(employment.getStatus()).isEqualTo(employmentDto.getEmploymentStatus());
        assertThat(employment.getEmployerINN()).isEqualTo(employmentDto.getEmployerINN());
        assertThat(employment.getSalary()).isEqualTo(employmentDto.getSalary());
        assertThat(employment.getPosition()).isEqualTo(employmentDto.getPosition());
        assertThat(employment.getWorkExperienceTotal()).isEqualTo(employmentDto.getWorkExperienceTotal());
        assertThat(employment.getWorkExperienceCurrent()).isEqualTo(employmentDto.getWorkExperienceCurrent());
    }

    @Test
    public void shouldProperlyMapEmploymentToEmploymentDto() {
        Employment employment = new Employment(
                EmploymentStatus.SELF_EMPLOYED,
                "1234567890",
                new BigDecimal("50000"),
                EmploymentPosition.WORKER,
                36,
                12
        );

        EmploymentDto employmentDto = mapper.toEmploymentDto(employment);

        assertThat(employmentDto).isNotNull();
        assertThat(employmentDto.getEmploymentStatus()).isEqualTo(employment.getStatus());
        assertThat(employmentDto.getEmployerINN()).isEqualTo(employment.getEmployerINN());
        assertThat(employmentDto.getSalary()).isEqualTo(employment.getSalary());
        assertThat(employmentDto.getPosition()).isEqualTo(employment.getPosition());
        assertThat(employmentDto.getWorkExperienceTotal()).isEqualTo(employment.getWorkExperienceTotal());
        assertThat(employmentDto.getWorkExperienceCurrent()).isEqualTo(employment.getWorkExperienceCurrent());
    }

}