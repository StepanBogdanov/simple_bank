package com.bogstepan.simple_bank.calculator_client.dto;

import com.bogstepan.simple_bank.calculator_client.annotation.MaximumAge;
import com.bogstepan.simple_bank.calculator_client.annotation.MinimumAge;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class LoanStatementRequestDto {

    @Schema(example = "200000")
    @DecimalMin(value = "30000", message = "������������� ����� ������ ���� �� ����� 30000")
    BigDecimal amount;

    @Schema(example = "24")
    @Min(value = 6, message = "���� ������� ������ ���� �� ����� 6 �������")
    Integer term;

    @Schema(example = "Aleksandr")
    @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "��� ������ ���� �������� ��������� � ����� ����� �� 2 �� 30 ��������")
    String firstName;

    @Schema(example = "Pushkin")
    @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "������� ������ ���� �������� ��������� � ����� ����� �� 2 �� 30 ��������")
    String lastName;

    @Schema(example = "Sergeevich")
    @Pattern(regexp = "^[a-zA-Z]{0,30}$", message = "��������, ��� �������, ������ ���� �������� ��������� � ����� ����� �� 2 �� 30 ��������")
    String middleName;

    @Schema(example = "pushkin@mail.ru")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Email ������ �������������� �������")
    String email;

    @Schema(format = "yyyy-mm-dd", example = "1980-06-06")
    @MinimumAge
    @MaximumAge
    LocalDate birthDate;

    @Schema(example = "1111")
    @Pattern(regexp = "^[0-9]{4}$", message = "����� �������� ������ ��������� ����� 4 �����")
    String passportSeries;

    @Schema(example = "123456")
    @Pattern(regexp = "^[0-9]{6}$", message = "����� �������� ������ ��������� ����� 6 ����")
    String passportNumber;
}
