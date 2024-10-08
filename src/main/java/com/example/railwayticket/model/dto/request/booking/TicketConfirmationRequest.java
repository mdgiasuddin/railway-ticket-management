package com.example.railwayticket.model.dto.request.booking;

import jakarta.validation.constraints.Size;

import java.util.List;

public record TicketConfirmationRequest(
        @Size(min = 1, max = 4)
        List<Long> ids
) {
}