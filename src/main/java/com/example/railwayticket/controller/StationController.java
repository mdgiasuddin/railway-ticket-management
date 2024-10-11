package com.example.railwayticket.controller;

import com.example.railwayticket.model.dto.response.StationResponse;
import com.example.railwayticket.service.intface.StationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    @Operation(summary = "Get list of all Stations")
    @GetMapping
    public List<StationResponse> getAllStations() {
        return stationService.getAllStations();
    }
}
