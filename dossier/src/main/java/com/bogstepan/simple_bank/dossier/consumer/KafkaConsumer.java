package com.bogstepan.simple_bank.dossier.consumer;

import com.bogstepan.simple_bank.clients.dto.EmailMessageDto;
import com.bogstepan.simple_bank.dossier.feign.DealFeignClient;
import com.bogstepan.simple_bank.dossier.service.LoanDocumentsService;
import com.bogstepan.simple_bank.dossier.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final MailSenderService mailSenderService;
    private final LoanDocumentsService loanDocumentsService;
    private final DealFeignClient dealFeignClient;

    @Bean
    public Consumer<EmailMessageDto> finishRegistrationRequest() {
        return message -> {
            log.info("Finish registration request for statement with id {} received", message.getStatementId());
            mailSenderService.sendFinishRegistrationMail(message);
        };
    }

    @Bean
    public Consumer<EmailMessageDto> createDocumentsRequest() {
        return message -> {
            log.info("Create documents request for statement with id {} received", message.getStatementId());
            mailSenderService.sendCreateDocumentsMail(message);
        };
    }

    @Bean
    public Consumer<EmailMessageDto> sendDocumentsRequest() {
        return message -> {
            log.info("Send documents request for statement with id {} received", message.getStatementId());
            loanDocumentsService.createLoanDocuments(message.getStatementId());
            dealFeignClient.updateStatementStatus(message.getStatementId());
            mailSenderService.sendLoanDocumentsMail(message);
        };
    }

    @Bean
    public Consumer<EmailMessageDto> signDocumentsRequest() {
        return message -> {
            log.info("Sign documents request for statement with id {} received", message.getStatementId());
            mailSenderService.sendSignDocumentsMail(message);
        };
    }

    @Bean
    public Consumer<EmailMessageDto> creditIssueRequest() {
        return message -> {
            log.info("Credit issue request for statement with id {} received", message.getStatementId());
            mailSenderService.sendCreditIssuedMail(message);
        };
    }

    @Bean
    public Consumer<EmailMessageDto> statementDeniedRequest() {
        return message -> {
            log.info("Statement denied request for statement with id {} received", message.getStatementId());
            mailSenderService.sendStatementDeniedMail(message);
        };
    }
}
