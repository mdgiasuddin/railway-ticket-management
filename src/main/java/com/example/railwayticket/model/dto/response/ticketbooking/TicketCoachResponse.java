package com.example.railwayticket.model.dto.response.ticketbooking;

import com.example.railwayticket.model.enums.SeatClass;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TicketCoachResponse {
    private String coachName;
    private SeatClass seatClass;
    private double fare;
    private int availableSeats;
    private List<Long> seatOrientation = new ArrayList<>();
    private List<TicketSeatResponse> seats = new ArrayList<>();
}
