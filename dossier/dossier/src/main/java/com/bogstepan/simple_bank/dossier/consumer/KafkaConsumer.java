package com.bogstepan.simple_bank.dossier.consumer;

import com.bogstepan.simple_bank.calculator_client.dto.EmailMessageDto;
import com.bogstepan.simple_bank.dossier.feign.DealFeignClient;
import com.bogstepan.simple_bank.dossier.service.LoanDocumentsService;
import com.bogstepan.simple_bank.dossier.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
            //todo: реализовать формирование документов
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
