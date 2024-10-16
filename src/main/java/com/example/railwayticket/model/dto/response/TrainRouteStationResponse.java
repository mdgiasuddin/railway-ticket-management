package com.example.railwayticket.model.dto.response;

import com.example.railwayticket.model.entity.TrainRouteStation;
import lombok.Getter;

@Getter
public class TrainRouteStationResponse {
    private final Long id;
    private final StationResponse station;
    private final int timeFromStartStation;

    public TrainRouteStationResponse(TrainRouteStation trainRouteStation) {
        this.id = trainRouteStation.getId();
        this.station = new StationResponse(trainRouteStation.getStation());
        this.timeFromStartStation = trainRouteStation.getTimeFromStartStation();
    }
}
