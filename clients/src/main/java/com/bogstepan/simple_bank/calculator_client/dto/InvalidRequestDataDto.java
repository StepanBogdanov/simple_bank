package com.bogstepan.simple_bank.calculator_client.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvalidRequestDataDto {

    @Schema(example = "Calculation error")
    private String info;
}
