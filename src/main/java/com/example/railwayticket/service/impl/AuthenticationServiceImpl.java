package com.example.railwayticket.service.impl;

import com.example.railwayticket.configuration.security.JwtService;
import com.example.railwayticket.model.dto.request.authentication.AuthenticationRequest;
import com.example.railwayticket.model.dto.request.authentication.UserRegistrationRequest;
import com.example.railwayticket.model.dto.response.AuthenticationResponse;
import com.example.railwayticket.model.dto.response.UserResponse;
import com.example.railwayticket.model.entity.User;
import com.example.railwayticket.service.intface.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(), request.password()
                )
        );

        User user = (User) authentication.getPrincipal();

        return new AuthenticationResponse(jwtService.generateAccessToken(user));
    }

    @Override
    public UserResponse register(UserRegistrationRequest request) {
        return null;
    }
}
