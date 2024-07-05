package com.bogstepan.simple_bank.dossier.consumer;

import com.bogstepan.simple_bank.calculator_client.dto.EmailMessageDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class KafkaConsumer {

    @Bean
    public Consumer<EmailMessageDto> finishRegistrationRequest() {
        return message -> System.out.println("Received: " + message);
    }
}
