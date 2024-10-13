package com.example.railwayticket.model.dto.response;

import com.example.railwayticket.model.entity.Coach;
import com.example.railwayticket.model.entity.Seat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@Getter
@Setter
public class SeatResponse {
    private long id;
    private String number;
    private String coach;
    private List<StationResponse> upStations;
    private List<StationResponse> downStations;

    public SeatResponse(Seat seat) {
        this.id = seat.getId();
        this.number = seat.getNumber();
    }

    public SeatResponse(Seat seat, Coach coach, List<StationResponse> upStations, List<StationResponse> downStations) {
        this.id = seat.getId();
        this.number = seat.getNumber();
        this.coach = coach.getName();
        this.upStations = upStations;
        this.downStations = downStations;
    }
}
