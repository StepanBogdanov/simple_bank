package com.bogstepan.simple_bank.statement.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionData {

    @Schema(example = "Ошибка получения кредитных предложений")
    private String info;
}
