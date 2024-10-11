package com.example.railwayticket.service.impl;

import com.example.railwayticket.exception.ResourceNotFoundException;
import com.example.railwayticket.exception.RuleViolationException;
import com.example.railwayticket.model.dto.request.coach.CoachCreateRequest;
import com.example.railwayticket.model.dto.request.coach.CoachUpdateRequest;
import com.example.railwayticket.model.dto.response.CoachResponse;
import com.example.railwayticket.model.entity.Coach;
import com.example.railwayticket.model.entity.Train;
import com.example.railwayticket.model.enumeration.TicketClass;
import com.example.railwayticket.repository.CoachRepository;
import com.example.railwayticket.service.intface.CoachService;
import com.example.railwayticket.service.intface.TrainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.railwayticket.constant.ExceptionCode.COACH_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoachServiceImpl implements CoachService {

    private final CoachRepository coachRepository;
    private final TrainService trainService;

    @Override
    public Coach getCoachById(long id) {
        return coachRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("No coach found with id: {}", id);
                    return new ResourceNotFoundException(
                            COACH_NOT_FOUND,
                            "No coach found with id: " + id);
                });
    }

    @Override
    public CoachResponse createNewCoach(CoachCreateRequest request) {
        Coach coach = new Coach();

        return updateCoachInformation(coach, request.totalSeats(), request.seatOrientation(),
                request.trainId(), request.name(), request.ticketClass());
    }

    @Override
    public List<CoachResponse> getCoachByTrainId(int trainId) {
        return coachRepository.getCoachByTrainId(trainId)
                .stream()
                .map(CoachResponse::new)
                .toList();
    }

    @Override
    public CoachResponse updateCoach(CoachUpdateRequest request) {
        Coach coach = getCoachById(request.id());

        return updateCoachInformation(coach, request.totalSeats(), request.seatOrientation(),
                request.trainId(), request.name(), request.ticketClass());
    }

    private CoachResponse updateCoachInformation(Coach coach, Integer totalSeats, List<Long> seatOrientation, Long trainId, String name, TicketClass ticketClass) {
        long sumOfSeats = seatOrientation
                .stream()
                .mapToLong(i -> i)
                .sum();

        if (sumOfSeats != totalSeats) {
            throw new RuleViolationException("INVALID_SEAT_ORIENTATION", "Seat orientation doesn't match with total seats");
        }

        Train train = trainService.getTrainById(trainId);
        coach.setName(name);
        coach.setTicketClass(ticketClass);
        coach.setTotalSeats(totalSeats);
        coach.setSeatOrientation(seatOrientation);
        coach.setTrain(train);

        return new CoachResponse(coachRepository.save(coach));
    }

}
