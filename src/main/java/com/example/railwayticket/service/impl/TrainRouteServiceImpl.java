package com.example.railwayticket.service.impl;

import com.example.railwayticket.exception.ResourceNotFoundException;
import com.example.railwayticket.model.dto.request.route.TrainRouteCreateRequest;
import com.example.railwayticket.model.dto.request.route.TrainRouteUpdateRequest;
import com.example.railwayticket.model.dto.response.TrainRouteResponse;
import com.example.railwayticket.model.entity.Route;
import com.example.railwayticket.model.entity.Train;
import com.example.railwayticket.model.entity.TrainRoute;
import com.example.railwayticket.model.enumeration.RouteType;
import com.example.railwayticket.repository.TrainRouteRepository;
import com.example.railwayticket.service.intface.RouteService;
import com.example.railwayticket.service.intface.TrainRouteService;
import com.example.railwayticket.service.intface.TrainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainRouteServiceImpl implements TrainRouteService {
    private final TrainRouteRepository trainRouteRepository;
    private final TrainService trainService;
    private final RouteService routeService;

    @Override
    public TrainRouteResponse createTrainRoute(TrainRouteCreateRequest request) {
        TrainRoute trainRoute = new TrainRoute();

        return fillTrainRouteInformation(trainRoute, request.trainId(), request.routeId(), request.offDay(),
                request.routeType(), request.startTime(), request.endTime(), request.description());
    }

    @Override
    public TrainRouteResponse updateTrainRoute(TrainRouteUpdateRequest request) {
        TrainRoute trainRoute = trainRouteRepository.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException("TRAIN_ROUTE_NOT_FOUND", String.format("No Train-Route mapping found with: %s", request.id())));

        return fillTrainRouteInformation(trainRoute, request.trainId(), request.routeId(), request.offDay(),
                request.routeType(), request.startTime(), request.endTime(), request.description());
    }

    private TrainRouteResponse fillTrainRouteInformation(TrainRoute trainRoute, Long trainId, Long routeId, DayOfWeek offDay,
                                                         RouteType routeType, LocalTime startTime, LocalTime endTime, String description) {

        Train train = trainService.getTrainById(trainId);
        Route route = routeService.getRouteByIdWithStations(routeId);

        trainRoute.setTrain(train);
        trainRoute.setRoute(route);
        trainRoute.setOffDay(offDay);
        trainRoute.setRouteType(routeType);
        trainRoute.setStartTime(startTime);
        trainRoute.setEndTime(endTime);
        trainRoute.setDescription(description);

        return new TrainRouteResponse(trainRouteRepository.save(trainRoute), train, route);
    }
}