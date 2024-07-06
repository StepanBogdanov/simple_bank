package com.bogstepan.simple_bank.deal.supplier;

import com.bogstepan.simple_bank.calculator_client.dto.EmailMessageDto;
import com.bogstepan.simple_bank.calculator_client.enums.Theme;
import com.bogstepan.simple_bank.deal.service.StatementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaSupplier {

    private final StatementService statementService;
    private final StreamBridge streamBridge;

    public void finishRegistrationRequest(String statementId) {
        streamBridge.send("finishRegistrationRequest-out-0", new EmailMessageDto(
                statementService.getById(statementId).getClient().getEmail(),
                Theme.FINISH_REGISTRATION,
                Math.abs(UUID.fromString(statementId).getLeastSignificantBits())
        ));
        log.info("Finish registration request for statement with id {} was sent to MS Dossier", statementId);
    }

    public void createDocumentsRequest(String statementId) {
        streamBridge.send("createDocumentsRequest-out-0", new EmailMessageDto(
                statementService.getById(statementId).getClient().getEmail(),
                Theme.CREATE_DOCUMENTS,
                Math.abs(UUID.fromString(statementId).getLeastSignificantBits())
        ));
        log.info("Create documents request for statement with id {} was sent to MS Dossier", statementId);
    }

    public void sendDocumentsRequest(String statementId) {
        streamBridge.send("sendDocumentsRequest-out-0", new EmailMessageDto(
                statementService.getById(statementId).getClient().getEmail(),
                Theme.SEND_DOCUMENTS,
                Math.abs(UUID.fromString(statementId).getLeastSignificantBits())
        ));
        log.info("Send documents request for statement with id {} was sent to MS Dossier", statementId);
    }

    public void signDocumentsRequest(String statementId) {
        streamBridge.send("signDocumentsRequest-out-0", new EmailMessageDto(
                statementService.getById(statementId).getClient().getEmail(),
                Theme.SEND_SES,
                Math.abs(UUID.fromString(statementId).getLeastSignificantBits())
        ));
        log.info("Sign document request for statement with id {} was sent to MS Dossier", statementId);
    }

    public void creditIssueRequest(String statementId) {
        streamBridge.send("creditIssueRequest-out-0", new EmailMessageDto(
                statementService.getById(statementId).getClient().getEmail(),
                Theme.CREDIT_ISSUED,
                Math.abs(UUID.fromString(statementId).getLeastSignificantBits())
        ));
        log.info("Credit issue request for statement with id {} was sent to MS Dossier", statementId);
    }
}
