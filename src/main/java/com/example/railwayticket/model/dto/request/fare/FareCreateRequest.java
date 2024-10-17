package com.example.railwayticket.model.dto.request.fare;

import com.example.railwayticket.model.enumeration.TicketClass;
import jakarta.validation.constraints.NotNull;

public record FareCreateRequest(
        @NotNull
        Long fromStationId,

        @NotNull
        Long toStationId,

        @NotNull
        TicketClass ticketClass,

        @NotNull
        Double fareAmount
) {
}
