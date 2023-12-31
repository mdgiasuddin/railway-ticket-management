package com.example.railwayticket.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    AD("ADMIN", "Admin"),
    M("MANAGER", "Manager"),
    AC("ACCOUNTANT", "Accountant"),
    CM("COUNTER_MASTER", "Counter Master"),
    U("USER", "User");

    private final String value;
    private final String details;
}
