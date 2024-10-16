package com.example.railwayticket.controller;

import com.example.railwayticket.model.dto.request.route.TrainRouteCreateRequest;
import com.example.railwayticket.model.dto.request.route.TrainRouteUpdateRequest;
import com.example.railwayticket.model.dto.response.TrainRouteResponse;
import com.example.railwayticket.service.intface.TrainRouteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/train-routes")
@RequiredArgsConstructor
public class TrainRouteController {

    private final TrainRouteService trainRouteService;

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
}
