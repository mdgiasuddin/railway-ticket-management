package com.example.railwayticket.service.intface;

import com.example.railwayticket.model.dto.response.StationResponse;
import com.example.railwayticket.model.entity.Station;

import java.util.List;

public interface StationService {
    Station getStationById(long stationId);

    List<StationResponse> getAllStations();
}
