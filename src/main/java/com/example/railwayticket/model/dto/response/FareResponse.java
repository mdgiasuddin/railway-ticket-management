package com.example.railwayticket.model.dto.response;

import com.example.railwayticket.model.entity.Fare;
import com.example.railwayticket.model.entity.Station;
import com.example.railwayticket.model.enumeration.TicketClass;
import lombok.Getter;

@Getter
public class FareResponse {
    private final long id;
    private final StationResponse fromStation;
    private final StationResponse toStation;
    private final TicketClass ticketClass;
    private final double fare;

    public FareResponse(Fare fare, Station fromStation, Station toStation) {
        this.id = fare.getId();
        this.fromStation = new StationResponse(fromStation);
        this.toStation = new StationResponse(toStation);
        this.ticketClass = fare.getTicketClass();
        this.fare = fare.getAmount();
    }
}
