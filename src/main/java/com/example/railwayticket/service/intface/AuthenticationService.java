package com.example.railwayticket.service.intface;

import com.example.railwayticket.model.dto.request.AuthenticationRequest;
import com.example.railwayticket.model.dto.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse login(AuthenticationRequest request);
}
