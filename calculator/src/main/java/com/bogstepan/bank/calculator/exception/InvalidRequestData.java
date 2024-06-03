package com.bogstepan.bank.calculator.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvalidRequestData {

    @Schema(example = "Calculation error")
    private String info;
}
