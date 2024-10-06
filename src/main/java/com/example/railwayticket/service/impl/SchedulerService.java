package com.example.railwayticket.service.impl;

import com.example.railwayticket.model.entity.Coach;
import com.example.railwayticket.model.entity.Fare;
import com.example.railwayticket.model.entity.Seat;
import com.example.railwayticket.model.entity.SeatForJourney;
import com.example.railwayticket.model.entity.TrainRoute;
import com.example.railwayticket.model.entity.TrainRouteStation;
import com.example.railwayticket.repository.FairRepository;
import com.example.railwayticket.repository.SeatForJourneyRepository;
import com.example.railwayticket.repository.TrainRouteRepository;
import com.example.railwayticket.utils.MiscUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.example.railwayticket.model.enumeration.RouteType.UP;
import static com.example.railwayticket.model.enumeration.SeatStatus.AVAILABLE;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SchedulerService {

    private final TrainRouteRepository trainRouteRepository;
    private final FairRepository fairRepository;
    private final SeatForJourneyRepository seatForJourneyRepository;
    private final LocalDate journeyDate = LocalDate.parse("2024-10-05");

    @Transactional
//    @Scheduled(initialDelay = 1000)
    public void scheduleTrainJourney() {
        log.info("Scheduler started at: {}", LocalDateTime.now());
        int page = 0;
        int routeFetchSize = 100;
        Page<TrainRoute> trainRoutePage;

        do {
            Pageable pageable = PageRequest.of(page, routeFetchSize);
            trainRoutePage = trainRouteRepository.findTrainRoutesByIdIsNotNull(pageable);

            for (TrainRoute trainRoute : trainRoutePage) {
                if (!trainRoute.getTrain().isActive() || journeyDate.getDayOfWeek().equals(trainRoute.getOffDay()))
                    continue;
                createSeatsForJourney(trainRoute);
            }
            page += 1;
        } while (trainRoutePage.hasNext());
    }

    private void createSeatsForJourney(TrainRoute trainRoute) {
        Set<TrainRouteStation> trainRouteStations = trainRoute.getTrainRouteStations();
        Map<Long, TrainRouteStation> stationMap = buildStationMap(trainRouteStations);
        List<Long> stationIds = getStationIds(trainRouteStations);
        List<Fare> fares = fairRepository.getAllRouteFairs(stationIds);
        Map<String, Fare> fareMap = buildFairMap(fares);

        List<SeatForJourney> seatForJourneys = new ArrayList<>();
        for (Coach coach : trainRoute.getTrain().getCoaches()) {
            if (!coach.isActive())
                continue;

            for (Seat seat : coach.getSeats()) {
                List<Long> seatStationMapping = UP.equals(trainRoute.getRouteType()) ? seat.getUpStationMapping() : seat.getDownStationMapping();
                log.info("Seat: {}, Station mapping: {}", seat.getId(), seatStationMapping);
                for (int i = 0; i < seatStationMapping.size() - 1; i++) {
                    long fromStationId = seatStationMapping.get(i);
                    long toStationId = seatStationMapping.get(i + 1);

                    log.info("Seat: {}, From station {}, to station {}, class {}", seat.getId(), fromStationId, toStationId, coach.getSeatClass());

                    SeatForJourney seatForJourney = new SeatForJourney();
                    seatForJourney.setIdKey(MiscUtils.generateIdKey());
                    seatForJourney.setFromStation(stationMap.get(fromStationId).getStation());
                    seatForJourney.setToStation(stationMap.get(toStationId).getStation());
                    seatForJourney.setSeat(seat);
                    seatForJourney.setTrainRoute(trainRoute);
                    seatForJourney.setFare(fareMap.get(MiscUtils.generateFareKey(fromStationId, toStationId, coach.getSeatClass()))
                            .getAmount());
                    seatForJourney.setSeatStatus(AVAILABLE);
                    LocalDateTime journeyDateTime = LocalDateTime.of(journeyDate, trainRoute.getStartTime())
                            .plusMinutes(stationMap.get(fromStationId).getTimeFromStartStation());
                    seatForJourney.setJourneyDate(journeyDateTime.toLocalDate());
                    seatForJourney.setJourneyTime(journeyDateTime.toLocalTime());
                    seatForJourney.setDestinationArrivalTime(LocalDateTime.of(journeyDate, trainRoute.getStartTime())
                            .plusMinutes(stationMap.get(toStationId).getTimeFromStartStation()));
                    seatForJourneys.add(seatForJourney);
                }
            }
        }

        seatForJourneyRepository.saveAll(seatForJourneys);
    }

    private Map<Long, TrainRouteStation> buildStationMap(Collection<TrainRouteStation> trainRouteStations) {
        Map<Long, TrainRouteStation> stationMap = new HashMap<>();
        for (TrainRouteStation trainRouteStation : trainRouteStations) {
            stationMap.put(trainRouteStation.getStation().getId(), trainRouteStation);
        }
        return stationMap;
    }

    private List<Long> getStationIds(Collection<TrainRouteStation> trainRouteStations) {
        List<Long> stationIds = new ArrayList<>();
        for (TrainRouteStation trainRouteStation : trainRouteStations) {
            stationIds.add(trainRouteStation.getStation().getId());
        }

        return stationIds;
    }

    private Map<String, Fare> buildFairMap(Collection<Fare> fares) {
        Map<String, Fare> fairMap = new HashMap<>();
        for (Fare fare : fares) {
            fairMap.put(MiscUtils.generateFareKey(fare.getFromStation().getId(), fare.getToStation().getId(), fare.getSeatClass()), fare);
        }

        return fairMap;
    }
}
