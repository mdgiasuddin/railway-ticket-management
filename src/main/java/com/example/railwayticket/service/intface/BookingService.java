package com.example.railwayticket.service.intface;

import com.example.railwayticket.model.dto.request.booking.TicketBookingRequest;
import com.example.railwayticket.model.dto.request.booking.TicketConfirmationRequest;
import com.example.railwayticket.model.dto.response.ticketbooking.TicketSearchResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface BookingService {
    TicketSearchResponse searchTicket(long fromStationId, long toStationId, LocalDate journeyDate);

    void bookTicket(TicketBookingRequest request);

    ResponseEntity<Resource> confirmAndPrintTicket(TicketConfirmationRequest request);
}
