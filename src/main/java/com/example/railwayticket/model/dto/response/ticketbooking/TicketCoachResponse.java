package com.example.railwayticket.model.dto.response.ticketbooking;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TicketCoachResponse {
    private String coachName;
    private int availableSeats = 0;
    private List<Long> seatOrientation = new ArrayList<>();
    private List<TicketSeatResponse> seats = new ArrayList<>();
}
