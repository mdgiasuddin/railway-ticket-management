package com.example.railwayticket.model.dto.request.station;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StationUpdateRequest(
        @NotNull
        Long id,

        @NotBlank
        String name,

        @NotBlank
        String description
) {
}
