package com.example.railwayticket.service.impl;

import com.example.railwayticket.model.dto.response.TicketSearchResponse;
import com.example.railwayticket.model.entity.Coach;
import com.example.railwayticket.model.entity.SeatForJourney;
import com.example.railwayticket.model.entity.Train;
import com.example.railwayticket.repository.SeatForJourneyRepository;
import com.example.railwayticket.service.intface.TicketBookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketBookingServiceImpl implements TicketBookingService {
    private final SeatForJourneyRepository seatForJourneyRepository;

    @Override
    public TicketSearchResponse searchTicket(long fromStationId, long toStationId, LocalDate journeyDate) {
        log.info("From station: {}, to station: {}, date: {}", fromStationId, toStationId, journeyDate);

        List<SeatForJourney> seatForJourneys = seatForJourneyRepository.searchSeatForJourney(fromStationId, toStationId, journeyDate);
        return null;
    }

    private Map<Train, List<Map.Entry<Coach, List<SeatForJourney>>>> organizeSeats(List<SeatForJourney> seatForJourneys) {
        Map<Coach, List<SeatForJourney>> coachSeatMap = new HashMap<>();

        for (SeatForJourney seatForJourney : seatForJourneys) {
            Coach coach = seatForJourney.getSeat().getCoach();
            List<SeatForJourney> defaultList = coachSeatMap.getOrDefault(coach, new ArrayList<>());
            defaultList.add(seatForJourney);
            coachSeatMap.put(coach, defaultList);
        }

        Map<Train, List<Map.Entry<Coach, List<SeatForJourney>>>> trainCoachMap = new HashMap<>();
        for (Map.Entry<Coach, List<SeatForJourney>> entry : coachSeatMap.entrySet()) {
            Train train = entry.getKey().getTrain();
            List<Map.Entry<Coach, List<SeatForJourney>>> defaultList = trainCoachMap.getOrDefault(train, new ArrayList<>());
            defaultList.add(entry);
            trainCoachMap.put(train, defaultList);
        }

        return trainCoachMap;
    }
}
