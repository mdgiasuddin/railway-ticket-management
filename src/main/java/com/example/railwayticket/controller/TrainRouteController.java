package com.example.railwayticket.controller;

import com.example.railwayticket.model.dto.request.route.TrainRouteCreateRequest;
import com.example.railwayticket.model.dto.request.route.TrainRouteStationCreateRequest;
import com.example.railwayticket.model.dto.request.route.TrainRouteStationUpdateRequest;
import com.example.railwayticket.model.dto.request.route.TrainRouteUpdateRequest;
import com.example.railwayticket.model.dto.response.TrainRouteResponse;
import com.example.railwayticket.model.dto.response.TrainRouteStationResponse;
import com.example.railwayticket.service.intface.TrainRouteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/train-routes")
@RequiredArgsConstructor
public class TrainRouteController {

    private final TrainRouteService trainRouteService;

    @Operation(summary = "Get all Train-Route mappings")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public List<TrainRouteResponse> getAllTrainRoutes() {
        return trainRouteService.getAllTrainRoutes();
    }

    @Operation(summary = "Create a new Train-Route (Map a with a Route)")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public TrainRouteResponse createTrainRoute(@RequestBody @Valid TrainRouteCreateRequest request) {
        return trainRouteService.createTrainRoute(request);
    }

    @Operation(summary = "Update Train-Route mapping")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping
    public TrainRouteResponse updateTrainRoute(@RequestBody @Valid TrainRouteUpdateRequest request) {
        return trainRouteService.updateTrainRoute(request);
    }

    @Operation(summary = "Get all stoppage Stations of a Train-Route")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/stations/{trainRouteId}")
    public List<TrainRouteStationResponse> getStationsOfRoute(@PathVariable long trainRouteId) {
        return trainRouteService.getStationsOfRoute(trainRouteId);
    }

    @Operation(summary = "Create a stoppage Station of a Train-Route")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/stations")
    public TrainRouteStationResponse createTrainRouteStation(@RequestBody @Valid TrainRouteStationCreateRequest request) {
        return trainRouteService.createTrainRouteStation(request);
    }

    @Operation(summary = "Update a stoppage Station of a Train-Route")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/stations")
    public TrainRouteStationResponse updateTrainRouteStation(@RequestBody @Valid TrainRouteStationUpdateRequest request) {
        return trainRouteService.updateTrainRouteStation(request);
    }
}
