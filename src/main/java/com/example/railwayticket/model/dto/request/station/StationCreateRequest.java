package com.example.railwayticket.model.dto.request.station;

import jakarta.validation.constraints.NotBlank;

public record StationCreateRequest(
        @NotBlank
        String name,

        @NotBlank
        String description
) {
}
