package com.example.railwayticket.model.dto.request.fare;

import com.example.railwayticket.model.enumeration.TicketClass;
import jakarta.validation.constraints.NotNull;

public record FareUpdateRequest(
        @NotNull
        Long id,

        @NotNull
        Long fromStationId,

        @NotNull
        Long toStationId,

        @NotNull
        TicketClass ticketClass,

        @NotNull
        Double fare
) {
}
