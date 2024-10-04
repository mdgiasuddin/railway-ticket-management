package com.example.railwayticket.service.intface;

import com.example.railwayticket.model.dto.response.ticketbooking.TicketSearchResponse;

import java.time.LocalDate;

public interface TicketBookingService {
    TicketSearchResponse searchTicket(long fromStationId, long toStationId, LocalDate journeyDate);
}
