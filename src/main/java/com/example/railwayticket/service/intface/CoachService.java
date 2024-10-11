package com.example.railwayticket.service.intface;

import com.example.railwayticket.model.dto.request.coach.CoachCreateRequest;
import com.example.railwayticket.model.dto.request.coach.CoachUpdateRequest;
import com.example.railwayticket.model.dto.response.CoachResponse;
import com.example.railwayticket.model.entity.Coach;

import java.util.List;

public interface CoachService {
    Coach getCoachById(long id);

    CoachResponse createNewCoach(CoachCreateRequest request);

    List<CoachResponse> getCoachByTrainId(int trainId);

    CoachResponse updateCoach(CoachUpdateRequest request);
}
