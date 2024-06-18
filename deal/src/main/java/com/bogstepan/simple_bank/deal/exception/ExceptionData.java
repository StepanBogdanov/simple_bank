package com.bogstepan.simple_bank.deal.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionData {

    @Schema(example = "Statement is not found")
    private String info;
}
