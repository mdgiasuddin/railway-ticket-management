package com.example.railwayticket.controller;

import com.example.railwayticket.model.dto.request.booking.TicketBookingRequest;
import com.example.railwayticket.model.dto.request.booking.TicketConfirmationRequest;
import com.example.railwayticket.model.dto.response.ticketbooking.TicketSearchResponse;
import com.example.railwayticket.service.intface.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static com.example.railwayticket.utils.AppDateTimeUtils.DATE_PATTERN;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PreAuthorize("hasAnyRole('USER')")
    @Operation(summary = "Search for available ticket between the stations on the searched date")
    @GetMapping("/search-ticket")
    public TicketSearchResponse searchTicket(
            @RequestParam long fromStationId,
            @RequestParam long toStationId,
            @DateTimeFormat(pattern = DATE_PATTERN) @RequestParam LocalDate date
    ) {
        return bookingService.searchTicket(fromStationId, toStationId, date);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @Operation(summary = "Book a ticket")
    @PostMapping("/book-ticket")
    public void bookTicket(@RequestBody @Valid TicketBookingRequest request) {
        bookingService.bookTicket(request);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @Operation(summary = "Confirm booked ticket")
    @PostMapping("/confirm-ticket")
    public ResponseEntity<Resource> confirmAndPrintTicket(@RequestBody @Valid TicketConfirmationRequest request) {
        return bookingService.confirmAndPrintTicket(request);
    }

}