package com.example.railwayticket.service.impl;

import com.example.railwayticket.exception.ResourceNotFoundException;
import com.example.railwayticket.model.dto.request.train.TrainCreateRequest;
import com.example.railwayticket.model.dto.request.train.TrainStatusUpdateRequest;
import com.example.railwayticket.model.dto.request.train.TrainUpdateRequest;
import com.example.railwayticket.model.dto.response.StationResponse;
import com.example.railwayticket.model.dto.response.TrainResponse;
import com.example.railwayticket.model.entity.Train;
import com.example.railwayticket.model.enumeration.RouteType;
import com.example.railwayticket.repository.TrainRepository;
import com.example.railwayticket.repository.TrainRouteStationRepository;
import com.example.railwayticket.service.intface.TrainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.railwayticket.constant.ExceptionCode.TRAIN_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;
    private final TrainRouteStationRepository trainRouteStationRepository;

    @Override
    public Train getTrainById(long id) {
        return trainRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("No train found with id: {}", id);
                    return new ResourceNotFoundException(
                            TRAIN_NOT_FOUND,
                            "No train found with id: " + id);
                });
    }

    @Override
    public List<TrainResponse> getAllTrains() {
        return trainRepository.findAll()
                .stream()
                .map(TrainResponse::new)
                .toList();
    }

    @Override
    public TrainResponse createTrain(TrainCreateRequest request) {
        Train train = new Train();
        train.setName(request.name());
        train.setActive(request.active());
        return new TrainResponse(trainRepository.save(train));
    }

    @Override
    public TrainResponse updateTrain(TrainUpdateRequest request) {
        Train train = getTrainById(request.id());
        train.setName(request.name());
        train.setActive(request.active());
        return new TrainResponse(trainRepository.save(train));
    }

    @Override
    public TrainResponse updateTrainStatus(TrainStatusUpdateRequest request) {
        Train train = getTrainById(request.id());
        train.setActive(request.active());
        return new TrainResponse(trainRepository.save(train));
    }

    @Override
    public List<StationResponse> getListOfStoppageStation(long trainId, RouteType routeType) {
        return trainRouteStationRepository.getStationsOfTrainRoute(trainId, routeType)
                .stream()
                .map(StationResponse::new)
                .toList();
    }
}
