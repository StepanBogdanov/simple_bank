package com.bogstepan.simple_bank.deal.mapping;

import com.bogstepan.simple_bank.clients.dto.PassportDto;
import com.bogstepan.simple_bank.deal.model.json.Passport;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class PassportMapperTest {

    private final PassportMapper passportMapper = new PassportMapperImpl();

    @Test
    public void shouldProperlyPassportDtoToPassport() {
        var passportDto = new PassportDto(
                "1111",
                "123456",
                "branch",
                LocalDate.of(2000, 1, 1)
        );

        var passport = passportMapper.toPassport(passportDto);

        assertThat(passport).isNotNull();
        assertThat(passport.getSeries()).isEqualTo(passportDto.getSeries());
        assertThat(passport.getNumber()).isEqualTo(passportDto.getNumber());
        assertThat(passport.getIssueBranch()).isEqualTo(passportDto.getIssueBranch());
        assertThat(passport.getIssueDate()).isEqualTo(passportDto.getIssueDate());
    }

    @Test
    public void shouldProperlyPassportToPassportDto() {
        var passport = new Passport(
                "1111",
                "123456",
                "branch",
                LocalDate.of(2000, 1, 1)
        );

        var passportDto = passportMapper.toPassportDto(passport);

        assertThat(passportDto).isNotNull();
        assertThat(passportDto.getSeries()).isEqualTo(passport.getSeries());
        assertThat(passportDto.getNumber()).isEqualTo(passport.getNumber());
        assertThat(passportDto.getIssueBranch()).isEqualTo(passport.getIssueBranch());
        assertThat(passportDto.getIssueDate()).isEqualTo(passport.getIssueDate());
    }

}