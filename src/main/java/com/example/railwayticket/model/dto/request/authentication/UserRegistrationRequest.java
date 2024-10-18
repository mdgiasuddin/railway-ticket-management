package com.example.railwayticket.model.dto.request.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserRegistrationRequest(
        @NotBlank
        String name,

        @NotBlank
        String username,

        @NotBlank
        String password,

        @NotBlank @Pattern(regexp = "^01\\d{9}$")
        String mobileNumber,

        @NotBlank @Email
        String email,

        @NotBlank @Pattern(regexp = "^\\d{10,20}$")
        String nid
) {
}
