package com.example.railwayticket.service.impl;

import com.example.railwayticket.model.dto.response.ticketbooking.TicketCoachResponse;
import com.example.railwayticket.model.dto.response.ticketbooking.TicketSearchResponse;
import com.example.railwayticket.model.dto.response.ticketbooking.TicketSeatResponse;
import com.example.railwayticket.model.dto.response.ticketbooking.TicketTrainResponse;
import com.example.railwayticket.model.entity.Coach;
import com.example.railwayticket.model.entity.Seat;
import com.example.railwayticket.model.entity.SeatForJourney;
import com.example.railwayticket.model.entity.Train;
import com.example.railwayticket.repository.SeatForJourneyRepository;
import com.example.railwayticket.service.intface.TicketBookingService;
import com.example.railwayticket.utils.AppDateTimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.railwayticket.constant.ApplicationConstants.BOOKING_VALIDITY;
import static com.example.railwayticket.model.enumeration.SeatStatus.AVAILABLE;
import static com.example.railwayticket.model.enumeration.SeatStatus.BOOKED;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketBookingServiceImpl implements TicketBookingService {
    private final SeatForJourneyRepository seatForJourneyRepository;

    @Override
    public TicketSearchResponse searchTicket(long fromStationId, long toStationId, LocalDate journeyDate) {
        List<SeatForJourney> seatForJourneys = seatForJourneyRepository.searchSeatForJourney(
                fromStationId, toStationId, journeyDate, AVAILABLE, BOOKED,
                AppDateTimeUtils.nowInBD().minusMinutes(BOOKING_VALIDITY)
        );
        Map<Train, List<Map.Entry<Coach, List<SeatForJourney>>>> trainCoachMap = organizeSeats(seatForJourneys);

        return convertToResponse(trainCoachMap);
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

    private TicketSearchResponse convertToResponse(Map<Train, List<Map.Entry<Coach, List<SeatForJourney>>>> trainCoachMap) {
        TicketSearchResponse ticketSearchResponse = new TicketSearchResponse();
        for (Map.Entry<Train, List<Map.Entry<Coach, List<SeatForJourney>>>> trainEntry : trainCoachMap.entrySet()) {
            Train train = trainEntry.getKey();
            TicketTrainResponse trainResponse = new TicketTrainResponse();
            trainResponse.setTrainName(train.getName());

            for (Map.Entry<Coach, List<SeatForJourney>> coachEntry : trainEntry.getValue()) {
                Coach coach = coachEntry.getKey();
                TicketCoachResponse coachResponse = new TicketCoachResponse();
                coachResponse.setCoachName(coach.getName());
                coachResponse.setSeatClass(coach.getSeatClass());
                coachResponse.setSeatOrientation(coach.getSeatOrientation());

                List<SeatForJourney> seatForJourneys = coachEntry.getValue();
                coachResponse.setAvailableSeats(seatForJourneys.size());

                SeatForJourney firstSeatForJourney = seatForJourneys.getFirst();
                coachResponse.setFare(firstSeatForJourney.getFare());

                trainResponse.setRoute(firstSeatForJourney.getTrainRoute().getDescription());
                trainResponse.setDepartureTime(LocalDateTime.of(
                        firstSeatForJourney.getJourneyDate(),
                        firstSeatForJourney.getJourneyTime()
                ));
                trainResponse.setDestinationArrivalTime(firstSeatForJourney.getDestinationArrivalTime());

                Map<Long, SeatForJourney> seatForJourneyMap = buildSeatForJourneyMap(seatForJourneys);
                List<Seat> seats = coach.getSeats()
                        .stream()
                        .sorted(Comparator.comparing(Seat::getOrdering))
                        .toList();

                for (Seat seat : seats) {
                    TicketSeatResponse seatResponse = new TicketSeatResponse();
                    seatResponse.setSeatNumber(seat.getNumber());
                    if (seatForJourneyMap.containsKey(seat.getId())) {
                        SeatForJourney seatForJourney = seatForJourneyMap.get(seat.getId());
                        seatResponse.setId(seatForJourney.getId());
                        seatResponse.setIdKey(seatForJourney.getIdKey());
                        seatResponse.setAvailable(true);
                    }
                    coachResponse.getSeats().add(seatResponse);
                }
                trainResponse.getCoaches().add(coachResponse);
                trainResponse.setAvailableSeats(trainResponse.getAvailableSeats() + coachResponse.getAvailableSeats());
            }
            ticketSearchResponse.getTrains().add(trainResponse);
        }

        return ticketSearchResponse;
    }

    private Map<Long, SeatForJourney> buildSeatForJourneyMap(List<SeatForJourney> seatForJourneys) {
        Map<Long, SeatForJourney> seatForJourneyMap = new HashMap<>();
        for (SeatForJourney seatForJourney : seatForJourneys) {
            seatForJourneyMap.put(seatForJourney.getSeat().getId(), seatForJourney);
        }
        return seatForJourneyMap;
    }
}
