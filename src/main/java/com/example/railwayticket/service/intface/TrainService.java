package com.example.railwayticket.service.intface;

import com.example.railwayticket.model.dto.request.train.TrainCreateRequest;
import com.example.railwayticket.model.dto.request.train.TrainStatusUpdateRequest;
import com.example.railwayticket.model.dto.request.train.TrainUpdateRequest;
import com.example.railwayticket.model.dto.response.TrainResponse;
import com.example.railwayticket.model.entity.Train;

import java.util.List;

public interface TrainService {

    Train getTrainById(long id);

    List<TrainResponse> getAllTrains();

    TrainResponse createTrain(TrainCreateRequest request);

    TrainResponse updateTrain(TrainUpdateRequest request);

    TrainResponse updateTrainStatus(TrainStatusUpdateRequest request);
}
