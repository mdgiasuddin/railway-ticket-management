package com.example.railwayticket.model.dto.request.route;

import com.example.railwayticket.model.enumeration.RouteType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record TrainRouteCreateRequest(
        @NotNull
        Long trainId,

        @NotNull
        Long routeId,

        @NotNull
        DayOfWeek offDay,

        @NotNull
        RouteType routeType,

        @NotNull
        @JsonFormat(pattern = "HH:mm")
        LocalTime startTime,

        @NotNull
        @JsonFormat(pattern = "HH:mm")
        LocalTime endTime,

        @NotBlank
        String description
) {
}
