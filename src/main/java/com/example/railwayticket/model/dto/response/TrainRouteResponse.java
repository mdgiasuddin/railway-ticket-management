package com.example.railwayticket.model.dto.response;

import com.example.railwayticket.model.entity.Route;
import com.example.railwayticket.model.entity.Train;
import com.example.railwayticket.model.entity.TrainRoute;
import com.example.railwayticket.model.enumeration.RouteType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
public class TrainRouteResponse {
    private final long id;
    private final TrainResponse train;
    private final RouteResponse route;
    private final DayOfWeek offDay;
    private final RouteType routeType;
    @JsonFormat(pattern = "HH:mm")
    private final LocalTime startTime;
    @JsonFormat(pattern = "HH:mm")
    private final LocalTime endTime;
    private final String description;

    public TrainRouteResponse(TrainRoute trainRoute, Train train, Route route) {
        this.id = trainRoute.getId();
        this.train = new TrainResponse(train);
        this.route = new RouteResponse(route, route.getStartStation(), route.getEndStation());
        this.offDay = trainRoute.getOffDay();
        this.routeType = trainRoute.getRouteType();
        this.startTime = trainRoute.getStartTime();
        this.endTime = trainRoute.getEndTime();
        this.description = trainRoute.getDescription();
    }
}
