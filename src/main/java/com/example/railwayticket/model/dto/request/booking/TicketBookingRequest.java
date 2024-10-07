package com.example.railwayticket.model.dto.request.booking;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

public record TicketBookingRequest(
        @NonNull
        Long id,

        @NotBlank
        String idKey
) {
}
