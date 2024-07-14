package com.bogstepan.simple_bank.dossier.consumer;

import com.bogstepan.simple_bank.clients.dto.EmailMessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class EmailMessageDeserializer implements Deserializer<EmailMessageDto> {

    private final ObjectMapper mapper = new ObjectMapper();


    @Override
    public EmailMessageDto deserialize(String topic, byte[] data) {
        try {
            return mapper.readValue(new String(data), EmailMessageDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
            //todo: добавить свой эксепшн и лог
        }
    }
}
