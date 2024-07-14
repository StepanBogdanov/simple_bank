package com.bogstepan.simple_bank.dossier.consumer;

import com.bogstepan.simple_bank.clients.dto.EmailMessageDto;
import com.bogstepan.simple_bank.dossier.feign.DealFeignClient;
import com.bogstepan.simple_bank.dossier.service.LoanDocumentsService;
import com.bogstepan.simple_bank.dossier.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumer {

    private final MailSenderService mailSenderService;
    private final LoanDocumentsService loanDocumentsService;
    private final DealFeignClient dealFeignClient;

    @Bean
    public Consumer<EmailMessageDto> finishRegistrationRequest() {
        return mailSenderService::sendFinishRegistrationMail;
    }

    @Bean
    public Consumer<EmailMessageDto> createDocumentsRequest() {
        return mailSenderService::sendCreateDocumentsMail;
    }

    @Bean
    public Consumer<EmailMessageDto> sendDocumentsRequest() {
        return message -> {
            loanDocumentsService.createLoanDocuments(message.getStatementId());
            dealFeignClient.updateStatementStatus(message.getStatementId());
            mailSenderService.sendLoanDocumentsMail(message);
        };
    }

    @Bean
    public Consumer<EmailMessageDto> signDocumentsRequest() {
        return mailSenderService::sendSignDocumentsMail;
    }

    @Bean
    public Consumer<EmailMessageDto> creditIssueRequest() {
        return mailSenderService::sendCreditIssuedMail;
    }
}
