package com.example.railwayticket.model.dto.response;

import com.example.railwayticket.model.entity.Route;
import com.example.railwayticket.model.entity.Station;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RouteResponse {
    private long id;
    private StationResponse startStation;
    private StationResponse endStation;

    public RouteResponse(Route route, Station startStation, Station endStation) {
        this.id = route.getId();
        this.startStation = new StationResponse(startStation);
        this.endStation = new StationResponse(endStation);
    }
}
