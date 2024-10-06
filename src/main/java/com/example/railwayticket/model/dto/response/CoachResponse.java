package com.example.railwayticket.model.dto.response;

import com.example.railwayticket.model.entity.Coach;
import com.example.railwayticket.model.enumeration.SeatClass;
import lombok.Getter;

@Getter
public class CoachResponse {
    private final long id;
    private final String name;
    private final SeatClass seatClass;
    private final int totalSeats;
    private final boolean active;

    public CoachResponse(Coach coach) {
        this.id = coach.getId();
        this.name = coach.getName();
        this.seatClass = coach.getSeatClass();
        this.totalSeats = coach.getTotalSeats();
        this.active = coach.isActive();
    }
}
