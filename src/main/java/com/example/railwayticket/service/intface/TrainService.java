package com.example.railwayticket.service.intface;

import com.example.railwayticket.model.dto.request.train.TrainCreateRequest;
import com.example.railwayticket.model.dto.request.train.TrainUpdateRequest;
import com.example.railwayticket.model.dto.response.TrainResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface TrainService {
    List<TrainResponse> getAllTrains();

    TrainResponse createTrain(@Valid TrainCreateRequest request);

    TrainResponse updateTrain(@Valid TrainUpdateRequest request);

}
