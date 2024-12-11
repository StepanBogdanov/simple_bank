package com.bogstepan.simple_bank.deal.supplier;

import com.bogstepan.simple_bank.clients.dto.EmailMessageDto;
import com.bogstepan.simple_bank.clients.enums.Theme;
import com.bogstepan.simple_bank.deal.service.StatementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

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
                statementId
        ));
        log.info("Finish registration request for statement with id {} was sent to MS Dossier", statementId);
    }

    public void createDocumentsRequest(String statementId) {
        streamBridge.send("createDocumentsRequest-out-0", new EmailMessageDto(
                statementService.getById(statementId).getClient().getEmail(),
                Theme.CREATE_DOCUMENTS,
                statementId
        ));
        log.info("Create documents request for statement with id {} was sent to MS Dossier", statementId);
    }

    public void sendDocumentsRequest(String statementId) {
        streamBridge.send("sendDocumentsRequest-out-0", new EmailMessageDto(
                statementService.getById(statementId).getClient().getEmail(),
                Theme.SEND_DOCUMENTS,
                statementId
        ));
        log.info("Send documents request for statement with id {} was sent to MS Dossier", statementId);
    }

    public void signDocumentsRequest(String statementId) {
        streamBridge.send("signDocumentsRequest-out-0", new EmailMessageDto(
                statementService.getById(statementId).getClient().getEmail(),
                Theme.SEND_SES,
                statementId
        ));
        log.info("Sign document request for statement with id {} was sent to MS Dossier", statementId);
    }

    public void creditIssueRequest(String statementId) {
        streamBridge.send("creditIssueRequest-out-0", new EmailMessageDto(
                statementService.getById(statementId).getClient().getEmail(),
                Theme.CREDIT_ISSUED,
                statementId
        ));
        log.info("Credit issue request for statement with id {} was sent to MS Dossier", statementId);
    }

    public void statementDeniedRequest(String statementId) {
        streamBridge.send("statementDeniedRequest-out-0", new EmailMessageDto(
                statementService.getById(statementId).getClient().getEmail(),
                Theme.STATEMENT_DENIED,
                statementId
        ));
    }
}
