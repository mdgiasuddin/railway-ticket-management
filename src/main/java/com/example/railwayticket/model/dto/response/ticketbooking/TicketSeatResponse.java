package com.example.railwayticket.model.dto.response.ticketbooking;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@JsonInclude(NON_NULL)
public class TicketSeatResponse {
    private Long id;
    private String idKey;
    private String seatNumber;
    private boolean available = false;
}
