package com.example.railwayticket.service.intface;

import com.example.railwayticket.model.dto.request.route.RouteCreateRequest;
import com.example.railwayticket.model.dto.request.route.RouteUpdateRequest;
import com.example.railwayticket.model.dto.response.RouteResponse;

public interface RouteService {
    RouteResponse createNewRoute(RouteCreateRequest request);

    RouteResponse updateRoute(RouteUpdateRequest request);
}
