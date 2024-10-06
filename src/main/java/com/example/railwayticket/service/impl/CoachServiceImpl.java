package com.example.railwayticket.service.impl;

import com.example.railwayticket.exception.RuleViolationException;
import com.example.railwayticket.model.dto.request.coach.CoachCreateRequest;
import com.example.railwayticket.model.dto.response.CoachResponse;
import com.example.railwayticket.model.entity.Coach;
import com.example.railwayticket.model.entity.Train;
import com.example.railwayticket.repository.CoachRepository;
import com.example.railwayticket.service.intface.CoachService;
import com.example.railwayticket.service.intface.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoachServiceImpl implements CoachService {

    private final CoachRepository coachRepository;
    private final TrainService trainService;

    @Override
    public CoachResponse createNewCoach(CoachCreateRequest request) {
        long sumOfSeats = request.seatOrientation()
                .stream()
                .mapToLong(i -> i)
                .sum();

        if (sumOfSeats != request.totalSeats()) {
            throw new RuleViolationException("INVALID_SEAT_ORIENTATION", "Seat orientation doesn't match with total seats");
        }

        Train train = trainService.getTrainById(request.trainId());
        Coach coach = new Coach();
        coach.setName(request.name());
        coach.setSeatClass(request.seatClass());
        coach.setTotalSeats(request.totalSeats());
        coach.setSeatOrientation(request.seatOrientation());
        coach.setTrain(train);

        return new CoachResponse(coachRepository.save(coach));
    }

    @Override
    public List<CoachResponse> getCoachByTrainId(int trainId) {
        return coachRepository.getCoachByTrainId(trainId)
                .stream()
                .map(CoachResponse::new)
                .toList();
    }
}
