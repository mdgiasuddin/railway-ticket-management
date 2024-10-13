package com.example.railwayticket.model.dto.request.seat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SeatUpdateRequest(

        @NotNull
        Long id,

        @NotBlank
        String number,

        @NotNull
        Long coachId,

        @NotNull
        Integer ordering,

        @NotNull
        List<Long> upStationIds,

        @NotNull
        List<Long> downStationIds
) {
}
