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

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final RouteRepository routeRepository;
    private final StationService stationService;

    @Override
    public List<RouteResponse> getAllRoutes() {
        return routeRepository.findAllByOrderById()
                .stream()
                .map(r -> new RouteResponse(r, r.getStartStation(), r.getEndStation()))
                .toList();
    }

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

        return fillRouteInformation(route, request.startStationId(), request.endStationId(), false);
    }

    @Override
    public RouteResponse updateRoute(RouteUpdateRequest request) {
        Route route = getRouteByIdWithStations(request.id());

        return fillRouteInformation(route, request.startStationId(), request.endStationId(), true);
    }

    private RouteResponse fillRouteInformation(Route route, Long startStationId, Long endStationId, boolean isUpdate) {
        if (startStationId.equals(endStationId)) {
            throw new RuleViolationException("SAME_START_AND_END_STATION", "Start station & End station must be different");
        }

        if (!isUpdate || !generateStationIdsPair(startStationId, endStationId).equals(generateStationIdsPair(route.getStartStation().getId(), route.getEndStation().getId()))) {
            if (routeRepository.getRouteByStationIds(startStationId, endStationId).isPresent()) {
                throw new RuleViolationException("ROUTE_ALREADY_EXISTS", "Route already exist");
            }
        }

        Station startStation = stationService.getStationById(startStationId);
        Station endtStation = stationService.getStationById(endStationId);

        route.setStartStation(startStation);
        route.setEndStation(endtStation);

        return new RouteResponse(routeRepository.save(route), startStation, endtStation);
    }

    private String generateStationIdsPair(long stationId1, long stationId2) {
        long max = Math.max(stationId1, stationId2);
        long min = Math.min(stationId1, stationId2);

        return String.format("%d,%d", min, max);
    }
}
