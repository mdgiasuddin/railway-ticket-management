package com.example.railwayticket.service.intface;

import com.example.railwayticket.model.dto.request.route.TrainRouteCreateRequest;
import com.example.railwayticket.model.dto.request.route.TrainRouteUpdateRequest;
import com.example.railwayticket.model.dto.response.TrainRouteResponse;

import java.util.List;

public interface TrainRouteService {
    List<TrainRouteResponse> getAllTrainRoutes();

    TrainRouteResponse createTrainRoute(TrainRouteCreateRequest request);

    TrainRouteResponse updateTrainRoute(TrainRouteUpdateRequest request);
}
