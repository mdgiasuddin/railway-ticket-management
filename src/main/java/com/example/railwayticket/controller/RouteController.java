package com.example.railwayticket.controller;

import com.example.railwayticket.model.dto.request.route.RouteCreateRequest;
import com.example.railwayticket.model.dto.request.route.RouteUpdateRequest;
import com.example.railwayticket.model.dto.response.RouteResponse;
import com.example.railwayticket.service.intface.RouteService;
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
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @Operation(summary = "Create a new Route entity in DB")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public RouteResponse createNewRoute(@RequestBody @Valid RouteCreateRequest request) {
        return routeService.createNewRoute(request);
    }

    @Operation(summary = "Update Route information")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping
    public RouteResponse updateRoute(@RequestBody @Valid RouteUpdateRequest request) {
        return routeService.updateRoute(request);
    }
}
