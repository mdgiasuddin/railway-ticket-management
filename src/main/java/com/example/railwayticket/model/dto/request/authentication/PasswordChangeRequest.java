package com.example.railwayticket.model.dto.request.authentication;

import jakarta.validation.constraints.NotBlank;

public record PasswordChangeRequest(
        @NotBlank
        String currentPassword,

        @NotBlank
        String newPassword,

        @NotBlank
        String confirmPassword
) {
}
