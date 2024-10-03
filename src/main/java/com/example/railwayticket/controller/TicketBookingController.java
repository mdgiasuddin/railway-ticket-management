package com.example.railwayticket.controller;

import com.example.railwayticket.model.dto.response.TicketSearchResponse;
import com.example.railwayticket.service.intface.TicketBookingService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static com.example.railwayticket.utils.AppDateTimeUtils.DATE_PATTERN;

@RestController
@RequestMapping("/api/ticket-booking")
@RequiredArgsConstructor
public class TicketBookingController {
    private final TicketBookingService ticketBookingService;

    @Operation(summary = "Search for available ticket between the stations on the searched date")
    @GetMapping("/search")
    public TicketSearchResponse searchTicket(
            @RequestParam long fromStationId,
            @RequestParam long toStationId,
            @DateTimeFormat(pattern = DATE_PATTERN) @RequestParam LocalDate date
    ) {
        return ticketBookingService.searchTicket(fromStationId, toStationId, date);
    }
}
