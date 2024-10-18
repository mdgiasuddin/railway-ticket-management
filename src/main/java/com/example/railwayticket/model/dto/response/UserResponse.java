package com.example.railwayticket.model.dto.response;

import com.example.railwayticket.model.entity.User;
import com.example.railwayticket.model.enumeration.Role;
import lombok.Getter;

@Getter
public class UserResponse {
    private final long id;
    private final String name;
    private final String username;
    private final String mobileNumber;
    private final String email;
    private final String nid;
    private final Role role;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.mobileNumber = user.getMobileNumber();
        this.email = user.getEmail();
        this.nid = user.getNid();
        this.role = user.getRole();
    }
}
