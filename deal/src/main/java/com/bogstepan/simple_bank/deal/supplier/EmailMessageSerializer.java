package com.bogstepan.simple_bank.deal.supplier;

import com.bogstepan.simple_bank.clients.dto.EmailMessageDto;
import com.bogstepan.simple_bank.deal.exception.RequestException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class EmailMessageSerializer implements Serializer<EmailMessageDto> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, EmailMessageDto data) {
        try {
            return mapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new RequestException(e.getMessage());
        }
    }
}
