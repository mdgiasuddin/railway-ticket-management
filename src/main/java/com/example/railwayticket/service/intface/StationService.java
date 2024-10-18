package com.example.railwayticket.service.intface;

import com.example.railwayticket.model.dto.request.station.StationCreateRequest;
import com.example.railwayticket.model.dto.request.station.StationUpdateRequest;
import com.example.railwayticket.model.dto.response.StationResponse;
import com.example.railwayticket.model.entity.Station;
import jakarta.validation.Valid;

import java.util.List;

public interface StationService {
    Station getStationById(long stationId);

    List<StationResponse> getAllStations();

    StationResponse createNewStation(StationCreateRequest request);

    StationResponse updateStation(@Valid StationUpdateRequest request);
}
