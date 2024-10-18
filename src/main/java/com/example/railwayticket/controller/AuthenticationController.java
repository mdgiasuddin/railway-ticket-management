package com.example.railwayticket.controller;

import com.example.railwayticket.model.dto.request.authentication.AuthenticationRequest;
import com.example.railwayticket.model.dto.request.authentication.PasswordChangeRequest;
import com.example.railwayticket.model.dto.request.authentication.UserRegistrationRequest;
import com.example.railwayticket.model.dto.response.AuthenticationResponse;
import com.example.railwayticket.model.dto.response.UserResponse;
import com.example.railwayticket.service.intface.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Login and get access-token using username & password.")
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody @Valid AuthenticationRequest request) {
        return authenticationService.login(request);
    }

    @Operation(summary = "Register a new User.")
    @PostMapping("/register")
    public UserResponse register(@RequestBody @Valid UserRegistrationRequest request) {
        return authenticationService.register(request);
    }

    @Operation(summary = "Change password.")
    @PostMapping("/change-password")
    public UserResponse changePassword(@RequestBody @Valid PasswordChangeRequest request) {
        return authenticationService.changePassword(request);
    }
}
