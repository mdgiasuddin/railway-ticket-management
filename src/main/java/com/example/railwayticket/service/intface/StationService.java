package com.example.railwayticket.service.intface;

import com.example.railwayticket.model.dto.response.StationResponse;

import java.util.List;

public interface StationService {
    List<StationResponse> getAllStations();
}
