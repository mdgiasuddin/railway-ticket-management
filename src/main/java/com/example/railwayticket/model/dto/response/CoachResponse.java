package com.example.railwayticket.model.dto.response;

import com.example.railwayticket.model.entity.Coach;
import com.example.railwayticket.model.enumeration.TicketClass;
import lombok.Getter;

import java.util.List;

@Getter
public class CoachResponse {
    private final long id;
    private final String name;
    private final TicketClass ticketClass;
    private final int totalSeats;
    private final boolean active;
    private final List<Long> seatOrientation;

    public CoachResponse(Coach coach) {
        this.id = coach.getId();
        this.name = coach.getName();
        this.ticketClass = coach.getTicketClass();
        this.totalSeats = coach.getTotalSeats();
        this.active = coach.isActive();
        this.seatOrientation = coach.getSeatOrientation();
    }
}
