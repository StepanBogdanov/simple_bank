package com.bogstepan.simple_bank.statement.dto;

import com.bogstepan.simple_bank.statement.validation.MaximumAge;
import com.bogstepan.simple_bank.statement.validation.MinimumAge;
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
    @DecimalMin(value = "30000", message = "Запрашиваемая сумма должна быть не менее 30000")
    BigDecimal amount;

    @Schema(example = "24")
    @Min(value = 6, message = "Срок кредита должен быть не менее 6 месяцев")
    Integer term;

    @Schema(example = "Aleksandr")
    @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "Имя должно быть написано латиницей и иметь длину от 2 до 30 символов")
    String firstName;

    @Schema(example = "Pushkin")
    @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "Фамилия должна быть написано латиницей и иметь длину от 2 до 30 символов")
    String lastName;

    @Schema(example = "Sergeevich")
    @Pattern(regexp = "^[a-zA-Z]{0,30}$", message = "Отчество, при наличии, должно быть написано латиницей и иметь длину от 2 до 30 символов")
    String middleName;

    @Schema(example = "pushkin@mail.ru")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Email должен соответсвовать шаблону")
    String email;

    @Schema(format = "yyyy-mm-dd", example = "1980-06-06")
    @MinimumAge
    @MaximumAge
    LocalDate birthDate;

    @Schema(example = "1111")
    @Pattern(regexp = "^[0-9]{4}$", message = "Серия паспорта должна содержать ровно 4 цифры")
    String passportSeries;

    @Schema(example = "123456")
    @Pattern(regexp = "^[0-9]{6}$", message = "Номер паспорта должен содержать ровно 6 цифр")
    String passportNumber;
}

