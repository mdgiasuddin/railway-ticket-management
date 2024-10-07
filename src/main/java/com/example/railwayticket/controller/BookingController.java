package com.example.railwayticket.controller;

import com.example.railwayticket.model.dto.request.booking.TicketBookingRequest;
import com.example.railwayticket.model.dto.response.ticketbooking.TicketSearchResponse;
import com.example.railwayticket.service.intface.BookingService;
import com.example.railwayticket.utils.AppDateTimeUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.example.railwayticket.utils.AppDateTimeUtils.DATE_PATTERN;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

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
    @GetMapping("/confirm-ticket")
    public ResponseEntity<Resource> confirmAndPrintTicket() {
        String filename = AppDateTimeUtils.nowInBD()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".pdf";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MULTIPART_FORM_DATA);
        httpHeaders.add("content-disposition", "attachment; filename=" + filename);
        return new ResponseEntity<>(bookingService.confirmAndPrintTicket(), httpHeaders, OK);
    }

}
