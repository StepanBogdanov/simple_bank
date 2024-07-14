package com.bogstepan.simple_bank.clients.dto;

import com.bogstepan.simple_bank.clients.enums.Theme;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessageDto {

    @Schema(example = "name@mail.ru")
    private String address;

    @Schema(example = "FINISH_REGISTRATION")
    private Theme theme;

    @Schema(example = "6564709385377260374")
    private String statementId;
}
