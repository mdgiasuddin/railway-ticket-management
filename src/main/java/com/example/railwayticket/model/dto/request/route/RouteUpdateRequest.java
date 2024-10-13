package com.example.railwayticket.model.dto.request.route;

import jakarta.validation.constraints.NotNull;

public record RouteUpdateRequest(
        @NotNull
        Long id,

        @NotNull
        Long startStationId,

        @NotNull
        Long endStationId
) {
}
