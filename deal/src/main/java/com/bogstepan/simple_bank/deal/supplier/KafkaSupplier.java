package com.bogstepan.simple_bank.deal.supplier;

import com.bogstepan.simple_bank.calculator_client.dto.EmailMessageDto;
import com.bogstepan.simple_bank.calculator_client.enums.Theme;
import com.bogstepan.simple_bank.deal.service.StatementService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KafkaSupplier {

    private final StatementService statementService;
    private final StreamBridge streamBridge;

    public void finishRegistrationRequest(String uuid) {
        streamBridge.send("finishRegistrationRequest-out-0", new EmailMessageDto(
                statementService.getById(uuid).getClient().getEmail(),
                Theme.FINISH_REGISTRATION,
                Math.abs(UUID.fromString(uuid).getLeastSignificantBits())
        ));
    }
}
