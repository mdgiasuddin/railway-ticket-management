package com.example.railwayticket.service.intface;

import com.example.railwayticket.model.dto.message.TicketData;

public interface KafkaMessagingService {
    void sendTicketEmail(TicketData ticketData);
}
