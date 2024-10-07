package com.example.railwayticket.service.intface;

import com.example.railwayticket.model.dto.request.booking.TicketBookingRequest;
import com.example.railwayticket.model.dto.response.ticketbooking.TicketSearchResponse;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;

import java.time.LocalDate;

public interface BookingService {
    TicketSearchResponse searchTicket(long fromStationId, long toStationId, LocalDate journeyDate);

    void bookTicket(@Valid TicketBookingRequest request);

    Resource confirmAndPrintTicket();
}
