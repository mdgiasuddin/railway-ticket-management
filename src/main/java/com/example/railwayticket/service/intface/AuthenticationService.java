package com.example.railwayticket.service.intface;

import com.example.railwayticket.model.dto.request.authentication.AuthenticationRequest;
import com.example.railwayticket.model.dto.request.authentication.UserRegistrationRequest;
import com.example.railwayticket.model.dto.response.AuthenticationResponse;
import com.example.railwayticket.model.dto.response.UserResponse;

public interface AuthenticationService {
    AuthenticationResponse login(AuthenticationRequest request);

    UserResponse register(UserRegistrationRequest request);
}
