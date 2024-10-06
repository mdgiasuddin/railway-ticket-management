package com.example.railwayticket.model.dto.request.train;

import jakarta.validation.constraints.NotNull;

public record TrainStatusUpdateRequest(
        @NotNull
        Long id,

        @NotNull
        Boolean active
) {
}
