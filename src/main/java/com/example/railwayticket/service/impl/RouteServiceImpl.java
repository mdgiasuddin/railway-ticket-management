package com.example.railwayticket.service.impl;

import com.example.railwayticket.exception.ResourceNotFoundException;
import com.example.railwayticket.exception.RuleViolationException;
import com.example.railwayticket.model.dto.request.route.RouteCreateRequest;
import com.example.railwayticket.model.dto.request.route.RouteUpdateRequest;
import com.example.railwayticket.model.dto.response.RouteResponse;
import com.example.railwayticket.model.entity.Route;
import com.example.railwayticket.model.entity.Station;
import com.example.railwayticket.repository.RouteRepository;
import com.example.railwayticket.service.intface.RouteService;
import com.example.railwayticket.service.intface.StationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final RouteRepository routeRepository;
    private final StationService stationService;

    @Override
    public Route getRouteById(Long id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ROUTE_NOT_FOUND", String.format("No route found with id %s", id)));
    }

    @Override
    public Route getRouteByIdWithStations(Long id) {
        return routeRepository.findRouteWithStationsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ROUTE_NOT_FOUND", String.format("No route found with id %s", id)));
    }

    @Override
    public RouteResponse createNewRoute(RouteCreateRequest request) {
        Route route = new Route();

        return fillRouteInformation(route, request.startStationId(), request.endStationId());
    }

    @Override
    public RouteResponse updateRoute(RouteUpdateRequest request) {
        Route route = getRouteById(request.id());

        return fillRouteInformation(route, request.startStationId(), request.endStationId());
    }

    private RouteResponse fillRouteInformation(Route route, Long startStationId, Long endStationId) {
        if (startStationId.equals(endStationId)) {
            throw new RuleViolationException("SAME_START_AND_END_STATION", "Start station & End station must be different");
        }

        Station startStation = stationService.getStationById(startStationId);
        Station endtStation = stationService.getStationById(endStationId);

        route.setStartStation(startStation);
        route.setEndStation(endtStation);

        return new RouteResponse(routeRepository.save(route), startStation, endtStation);
    }
}
