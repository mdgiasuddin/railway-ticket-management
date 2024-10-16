package com.example.railwayticket.service.intface;

import com.example.railwayticket.model.dto.request.route.RouteCreateRequest;
import com.example.railwayticket.model.dto.request.route.RouteUpdateRequest;
import com.example.railwayticket.model.dto.response.RouteResponse;
import com.example.railwayticket.model.entity.Route;

public interface RouteService {
    Route getRouteById(Long id);

    Route getRouteByIdWithStations(Long id);

    RouteResponse createNewRoute(RouteCreateRequest request);

    RouteResponse updateRoute(RouteUpdateRequest request);
}
