package com.example.railwayticket.service.impl;

import com.example.railwayticket.exception.ResourceNotFoundException;
import com.example.railwayticket.model.dto.request.train.TrainCreateRequest;
import com.example.railwayticket.model.dto.request.train.TrainUpdateRequest;
import com.example.railwayticket.model.dto.response.TrainResponse;
import com.example.railwayticket.model.entity.Train;
import com.example.railwayticket.repository.TrainRepository;
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
        Train train = trainRepository.findById(request.id())
                .orElseThrow(() -> {
                    log.error("No train found with id: {}", request.id());
                    return new ResourceNotFoundException(
                            TRAIN_NOT_FOUND,
                            "No train found with id: " + request.id());
                });

        train.setName(request.name());
        train.setActive(request.active());
        return new TrainResponse(trainRepository.save(train));
    }
}
