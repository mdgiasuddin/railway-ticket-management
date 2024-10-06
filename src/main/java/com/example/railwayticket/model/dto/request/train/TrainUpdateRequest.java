package com.example.railwayticket.model.dto.request.train;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TrainUpdateRequest(
        @NotNull
        Long id,

        @NotBlank
        String name,

        @NotNull
        Boolean active
) {
}
