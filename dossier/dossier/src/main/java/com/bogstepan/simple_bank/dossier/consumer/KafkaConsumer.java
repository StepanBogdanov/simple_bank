package com.bogstepan.simple_bank.dossier.consumer;

import com.bogstepan.simple_bank.calculator_client.dto.EmailMessageDto;
import com.bogstepan.simple_bank.dossier.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumer {

    private final MailSenderService mailSenderService;

    @Bean
    public Consumer<EmailMessageDto> finishRegistrationRequest() {
        return mailSenderService::sendFinishRegistrationMail;
    }

    @Bean
    public Consumer<EmailMessageDto> createDocumentsRequest() {
        return message -> System.out.println("Received: " + message);
    }

    @Bean
    public Consumer<EmailMessageDto> sendDocumentsRequest() {
        return message -> System.out.println("Received: " + message);
    }

    @Bean
    public Consumer<EmailMessageDto> signDocumentsRequest() {
        return message -> System.out.println("Received: " + message);
    }

    @Bean
    public Consumer<EmailMessageDto> creditIssueRequest() {
        return message -> System.out.println("Received: " + message);
    }
}
