package com.example.railwayticket.service.intface;

import com.example.railwayticket.model.dto.request.route.TrainRouteCreateRequest;
import com.example.railwayticket.model.dto.request.route.TrainRouteUpdateRequest;
import com.example.railwayticket.model.dto.response.TrainRouteResponse;

public interface TrainRouteService {
    TrainRouteResponse createTrainRoute(TrainRouteCreateRequest request);

    TrainRouteResponse updateTrainRoute(TrainRouteUpdateRequest request);
}
