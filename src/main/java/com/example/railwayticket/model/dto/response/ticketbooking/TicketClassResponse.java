package com.example.railwayticket.model.dto.response.ticketbooking;

import com.example.railwayticket.model.enumeration.TicketClass;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class TicketClassResponse {

    private TicketClass ticketClass;
    private double fare;
    private int availableSeats;
    private List<TicketCoachResponse> coaches;

    public TicketClassResponse(TicketClass ticketClass, double fare) {
        this.ticketClass = ticketClass;
        this.fare = fare;
        this.availableSeats = 0;
        this.coaches = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicketClassResponse that)) return false;
        return ticketClass == that.ticketClass;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ticketClass);
    }
}
