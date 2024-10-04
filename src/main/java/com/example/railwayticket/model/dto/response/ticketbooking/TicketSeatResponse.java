package com.example.railwayticket.model.dto.response.ticketbooking;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketSeatResponse {
    private long id;
    private String idKey;
    private String seatNumber;
}
