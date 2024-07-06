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

    public void createDocumentsRequest(String uuid) {
        streamBridge.send("createDocumentsRequest-out-0", new EmailMessageDto(
                statementService.getById(uuid).getClient().getEmail(),
                Theme.CREATE_DOCUMENTS,
                Math.abs(UUID.fromString(uuid).getLeastSignificantBits())
        ));
    }

    public void sendDocumentsRequest(String uuid) {
        streamBridge.send("sendDocumentsRequest-out-0", new EmailMessageDto(
                statementService.getById(uuid).getClient().getEmail(),
                Theme.SEND_DOCUMENTS,
                Math.abs(UUID.fromString(uuid).getLeastSignificantBits())
        ));
    }

    public void signDocumentsRequest(String uuid) {
        streamBridge.send("signDocumentsRequest-out-0", new EmailMessageDto(
                statementService.getById(uuid).getClient().getEmail(),
                Theme.SEND_SES,
                Math.abs(UUID.fromString(uuid).getLeastSignificantBits())
        ));
    }

    public void creditIssueRequest(String uuid) {
        streamBridge.send("creditIssueRequest-out-0", new EmailMessageDto(
                statementService.getById(uuid).getClient().getEmail(),
                Theme.CREDIT_ISSUED,
                Math.abs(UUID.fromString(uuid).getLeastSignificantBits())
        ));
    }
}
