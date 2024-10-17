package com.example.railwayticket.service.impl;

import com.example.railwayticket.exception.ResourceNotFoundException;
import com.example.railwayticket.model.dto.request.route.TrainRouteCreateRequest;
import com.example.railwayticket.model.dto.request.route.TrainRouteStationCreateRequest;
import com.example.railwayticket.model.dto.request.route.TrainRouteStationUpdateRequest;
import com.example.railwayticket.model.dto.request.route.TrainRouteUpdateRequest;
import com.example.railwayticket.model.dto.response.TrainRouteResponse;
import com.example.railwayticket.model.dto.response.TrainRouteStationResponse;
import com.example.railwayticket.model.entity.Route;
import com.example.railwayticket.model.entity.Station;
import com.example.railwayticket.model.entity.Train;
import com.example.railwayticket.model.entity.TrainRoute;
import com.example.railwayticket.model.entity.TrainRouteStation;
import com.example.railwayticket.model.enumeration.RouteType;
import com.example.railwayticket.repository.TrainRouteRepository;
import com.example.railwayticket.repository.TrainRouteStationRepository;
import com.example.railwayticket.service.intface.RouteService;
import com.example.railwayticket.service.intface.StationService;
import com.example.railwayticket.service.intface.TrainRouteService;
import com.example.railwayticket.service.intface.TrainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainRouteServiceImpl implements TrainRouteService {
    private final TrainRouteRepository trainRouteRepository;
    private final TrainRouteStationRepository trainRouteStationRepository;
    private final TrainService trainService;
    private final RouteService routeService;
    private final StationService stationService;

    private TrainRoute getTrainRoute(long id) {
        return trainRouteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TRAIN_ROUTE_NOT_FOUND", String.format("No Train-Route mapping found with: %s", id)));
    }

    @Override
    public List<TrainRouteResponse> getAllTrainRoutes() {
        return trainRouteRepository.findAllByOrderById()
                .stream()
                .map(tr -> new TrainRouteResponse(tr, tr.getTrain(), tr.getRoute()))
                .sorted(
                        Comparator.comparing(TrainRouteResponse::getRouteType)
                                .thenComparing(TrainRouteResponse::getStartTime)
                )
                .toList();
    }

    @Override
    public TrainRouteResponse createTrainRoute(TrainRouteCreateRequest request) {
        TrainRoute trainRoute = new TrainRoute();

        return fillTrainRouteInformation(trainRoute, request.trainId(), request.routeId(), request.offDay(),
                request.routeType(), request.startTime(), request.endTime(), request.description());
    }

    @Override
    public TrainRouteResponse updateTrainRoute(TrainRouteUpdateRequest request) {
        TrainRoute trainRoute = getTrainRoute(request.id());

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

    @Override
    public List<TrainRouteStationResponse> getStationsOfRoute(long trainRouteId) {
        return trainRouteStationRepository.getStationsOfTrainRoute(trainRouteId)
                .stream()
                .map(trs -> new TrainRouteStationResponse(trs, trs.getStation()))
                .toList();
    }

    @Override
    public TrainRouteStationResponse createTrainRouteStation(TrainRouteStationCreateRequest request) {
        TrainRouteStation trainRouteStation = new TrainRouteStation();

        return fillTrainRouteStationInformation(trainRouteStation, request.trainRouteId(), request.stationId(), request.timeFromStartStation());
    }

    @Override
    public TrainRouteStationResponse updateTrainRouteStation(TrainRouteStationUpdateRequest request) {
        TrainRouteStation trainRouteStation = trainRouteStationRepository.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException("TRAIN_ROUTE_STATION_NOT_FOUND", String.format("No Train-Route-Station mapping found with id: %s", request.id())));

        return fillTrainRouteStationInformation(trainRouteStation, request.trainRouteId(), request.stationId(), request.timeFromStartStation());
    }

    private TrainRouteStationResponse fillTrainRouteStationInformation(TrainRouteStation trainRouteStation, Long trainRouteId,
                                                                       Long stationId, Integer timeFromStartStation) {

        TrainRoute trainRoute = getTrainRoute(trainRouteId);
        Station station = stationService.getStationById(stationId);

        trainRouteStation.setTrainRoute(trainRoute);
        trainRouteStation.setStation(station);
        trainRouteStation.setTimeFromStartStation(timeFromStartStation);

        return new TrainRouteStationResponse(
                trainRouteStationRepository.save(trainRouteStation),
                station
        );
    }
}
