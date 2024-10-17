package com.example.railwayticket.model.dto.request.route;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TrainRouteStationUpdateRequest(
        @NotNull
        Long id,

        @NotNull
        Long trainRouteId,

        @NotNull
        Long stationId,

        @NotNull
        @Min(0)
        Integer timeFromStartStation
) {
}
