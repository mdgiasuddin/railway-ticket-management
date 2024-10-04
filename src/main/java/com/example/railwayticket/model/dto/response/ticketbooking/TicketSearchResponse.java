package com.example.railwayticket.model.dto.response.ticketbooking;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TicketSearchResponse {
    private List<TicketTrainResponse> trains = new ArrayList<>();
}
