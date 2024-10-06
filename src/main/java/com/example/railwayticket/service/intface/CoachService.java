package com.example.railwayticket.service.intface;

import com.example.railwayticket.model.dto.request.coach.CoachCreateRequest;
import com.example.railwayticket.model.dto.response.CoachResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface CoachService {
    CoachResponse createNewCoach(@Valid CoachCreateRequest request);

    List<CoachResponse> getCoachByTrainId(int trainId);
}
