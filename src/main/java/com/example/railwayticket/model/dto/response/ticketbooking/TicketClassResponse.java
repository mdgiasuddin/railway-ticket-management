package com.example.railwayticket.model.dto.response.ticketbooking;

import com.example.railwayticket.model.enumeration.SeatClass;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class TicketClassResponse {

    private SeatClass seatClass;
    private double fare;
    private int availableSeats;
    private List<TicketCoachResponse> coaches;

    public TicketClassResponse(SeatClass seatClass, double fare) {
        this.seatClass = seatClass;
        this.fare = fare;
        this.availableSeats = 0;
        this.coaches = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicketClassResponse that)) return false;
        return seatClass == that.seatClass;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(seatClass);
    }
}
