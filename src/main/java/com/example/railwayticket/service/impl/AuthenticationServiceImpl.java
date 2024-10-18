package com.example.railwayticket.service.impl;

import com.example.railwayticket.configuration.security.JwtService;
import com.example.railwayticket.exception.RuleViolationException;
import com.example.railwayticket.model.dto.request.authentication.AuthenticationRequest;
import com.example.railwayticket.model.dto.request.authentication.PasswordChangeRequest;
import com.example.railwayticket.model.dto.request.authentication.UserRegistrationRequest;
import com.example.railwayticket.model.dto.response.AuthenticationResponse;
import com.example.railwayticket.model.dto.response.UserResponse;
import com.example.railwayticket.model.entity.User;
import com.example.railwayticket.repository.UserRepository;
import com.example.railwayticket.service.intface.AuthenticationService;
import com.example.railwayticket.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.railwayticket.model.enumeration.Role.USER;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

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
        User user = new User();
        user.setName(request.name());
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setMobileNumber(request.mobileNumber());
        user.setEmail(request.email());
        user.setNid(request.nid());
        user.setRole(USER);

        return new UserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse changePassword(PasswordChangeRequest request) {
        User user = SecurityUtils.getCurrentUser()
                .orElseThrow(() -> {
                    log.error("User must be logged in to book a ticket");
                    return new RuleViolationException("LOGGED_IN_USER_NOT_FOUND", "User must be logged in to change password");
                });

        if (!request.newPassword().equals(request.confirmPassword())) {
            throw new RuleViolationException("CONFIRM_PASSWORD_MISMATCH", "Passwords do not match");
        }

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new RuleViolationException("WRONG_PASSWORD", "Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        return new UserResponse(userRepository.save(user));
    }
}
