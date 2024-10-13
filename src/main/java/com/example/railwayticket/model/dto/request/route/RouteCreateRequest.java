package com.example.railwayticket.model.dto.request.route;

import jakarta.validation.constraints.NotNull;

public record RouteCreateRequest(
        @NotNull
        Long startStationId,

        @NotNull
        Long endStationId
) {
}
