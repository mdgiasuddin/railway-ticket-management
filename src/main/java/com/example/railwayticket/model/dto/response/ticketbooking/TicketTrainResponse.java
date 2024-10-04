package com.example.railwayticket.model.dto.response.ticketbooking;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.railwayticket.utils.AppDateTimeUtils.DATE_TIME_PATTERN;

@Getter
@Setter
public class TicketTrainResponse {
    private String trainName;
    private String route;
    private int availableSeats = 0;
    private List<TicketCoachResponse> coaches = new ArrayList<>();
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime departureTime;
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime destinationArrivalTime;
}
