package com.example.railwayticket.model.dto.request.coach;

import com.example.railwayticket.model.enumeration.TicketClass;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CoachCreateRequest(
        @NotBlank
        String name,

        @NotNull
        TicketClass ticketClass,

        @NotNull
        Long trainId,

        @NotNull @Min(5)
        Integer totalSeats,

        @NotEmpty
        List<Long> seatOrientation,

        @NotNull
        Boolean active
) {
}
