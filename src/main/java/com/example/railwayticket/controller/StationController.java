package com.example.railwayticket.controller;

import com.example.railwayticket.model.dto.request.station.StationCreateRequest;
import com.example.railwayticket.model.dto.request.station.StationUpdateRequest;
import com.example.railwayticket.model.dto.response.StationResponse;
import com.example.railwayticket.service.intface.StationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Operation(summary = "Create new Station entity in DB")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public StationResponse createNewStation(@RequestBody @Valid StationCreateRequest request) {
        return stationService.createNewStation(request);
    }

    @Operation(summary = "Update Station information")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping
    public StationResponse updateStation(@RequestBody @Valid StationUpdateRequest request) {
        return stationService.updateStation(request);
    }
}
