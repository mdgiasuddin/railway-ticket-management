package com.example.railwayticket.service.impl;

import com.example.railwayticket.model.dto.message.TicketData;
import com.example.railwayticket.service.intface.KafkaMessagingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaMessagingServiceImpl implements KafkaMessagingService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${spring.kafka.ticket.email.topic-name}")
    private String emailTopicName;

    @Override
    public void sendTicketEmail(TicketData ticketData) {
        String messageString = "";
        try {
            messageString = objectMapper.writeValueAsString(ticketData);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }

        Message<String> message = MessageBuilder
                .withPayload(messageString)
                .setHeader(KafkaHeaders.TOPIC, emailTopicName)
                .build();

        kafkaTemplate.send(message);
        log.info("Message sent -> {}", ticketData);
    }
}
