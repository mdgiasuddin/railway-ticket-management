package com.example.railwayticket.service.impl;

import com.example.railwayticket.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecureRandom random;

    @PostConstruct
    public void createAdmin() {
        /*User user = new User();
        user.setName("Giash Uddin");
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setMobileNumber("012345678");
        user.setNid("012345678");
        user.setRole(Role.AD);
        userRepository.save(user);*/

        /*byte[] bytes = new byte[64];
        random.nextBytes(bytes);
        System.out.println("key: " + String.copyValueOf(Hex.encode(bytes)));*/
    }
}
