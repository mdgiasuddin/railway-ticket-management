package com.example.railwayticket.model.dto.response.ticketbooking;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TicketTrainResponse {
    private String trainName;
    private String route;
    private int availableSeats = 0;
    private List<TicketCoachResponse> coaches = new ArrayList<>();
    private LocalDateTime departureTime;
    private LocalDateTime destinationArrivalTime;
}
