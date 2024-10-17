package com.example.railwayticket.model.dto.response;

import com.example.railwayticket.model.entity.Station;
import com.example.railwayticket.model.entity.TrainRouteStation;
import lombok.Getter;

@Getter
public class TrainRouteStationResponse {
    private final Long id;
    private final StationResponse station;
    private final int timeFromStartStation;

    public TrainRouteStationResponse(TrainRouteStation trainRouteStation, Station station) {
        this.id = trainRouteStation.getId();
        this.station = new StationResponse(station);
        this.timeFromStartStation = trainRouteStation.getTimeFromStartStation();
    }
}
