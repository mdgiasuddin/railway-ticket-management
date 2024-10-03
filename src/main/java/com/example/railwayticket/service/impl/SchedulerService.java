package com.example.railwayticket.service.impl;

import com.example.railwayticket.model.entity.Coach;
import com.example.railwayticket.model.entity.Fair;
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

import static com.example.railwayticket.model.enums.RouteType.UP;
import static com.example.railwayticket.model.enums.SeatStatus.AVAILABLE;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SchedulerService {

    private final TrainRouteRepository trainRouteRepository;
    private final FairRepository fairRepository;
    private final SeatForJourneyRepository seatForJourneyRepository;
    private final LocalDate journeyDate = LocalDate.parse("2024-10-04");

    @Transactional
//    @Scheduled(initialDelay = 1000)
    public void schedule() {
        log.info("Scheduler started at: {}", LocalDateTime.now());
        int page = 0;
        int routeFetchSize = 100;
        Page<TrainRoute> trainRoutePage;

        do {
            Pageable pageable = PageRequest.of(page, routeFetchSize);
            trainRoutePage = trainRouteRepository.findTrainRoutesByIdIsNotNull(pageable);

            for (TrainRoute trainRoute : trainRoutePage) {
                createSeatsForJourney(trainRoute);
            }
            page += 1;
        } while (trainRoutePage.hasNext());
    }

    private void createSeatsForJourney(TrainRoute trainRoute) {
        if (!journeyDate.getDayOfWeek().equals(trainRoute.getOffDay())) {
            Set<TrainRouteStation> trainRouteStations = trainRoute.getTrainRouteStations();
            Map<Long, TrainRouteStation> stationMap = buildStationMap(trainRouteStations);
            List<Long> stationIds = getStationIds(trainRouteStations);
            List<Fair> fairs = fairRepository.getAllRouteFairs(stationIds);
            Map<String, Fair> fairMap = buildFairMap(fairs);

            List<SeatForJourney> seatForJourneys = new ArrayList<>();
            for (Coach coach : trainRoute.getTrain().getCoaches()) {
                for (Seat seat : coach.getSeats()) {
                    List<Long> seatStationMapping = UP.equals(trainRoute.getRouteType()) ? seat.getUpStationMapping() : seat.getDownStationMapping();
                    for (int i = 0; i < seatStationMapping.size() - 1; i++) {
                        long fromStationId = stationIds.get(i);
                        long toStationId = stationIds.get(i + 1);

                        log.info("From station {}, to station {}, class {}", fromStationId, toStationId, coach.getSeatClass());

                        SeatForJourney seatForJourney = new SeatForJourney();
                        seatForJourney.setFromStation(stationMap.get(fromStationId).getStation());
                        seatForJourney.setToStation(stationMap.get(toStationId).getStation());
                        seatForJourney.setSeat(seat);
                        seatForJourney.setTrainRoute(trainRoute);
                        seatForJourney.setFair(fairMap.get(MiscUtils.generateFairKey(fromStationId, toStationId, coach.getSeatClass()))
                                .getAmount());
                        seatForJourney.setSeatStatus(AVAILABLE);
                        LocalDateTime journeyDateTime = LocalDateTime.of(journeyDate, trainRoute.getStartTime())
                                .plusMinutes(stationMap.get(fromStationId).getTimeFromStartStation());
                        seatForJourney.setJourneyDate(journeyDateTime.toLocalDate());
                        seatForJourney.setJourneyTime(journeyDateTime.toLocalTime());
                        seatForJourney.setJourneyEndTime(LocalDateTime.of(journeyDate, trainRoute.getStartTime())
                                .plusMinutes(stationMap.get(toStationId).getTimeFromStartStation()));
                        seatForJourneys.add(seatForJourney);
                    }
                }
            }

            seatForJourneyRepository.saveAll(seatForJourneys);
        }
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

    private Map<String, Fair> buildFairMap(Collection<Fair> fairs) {
        Map<String, Fair> fairMap = new HashMap<>();
        for (Fair fair : fairs) {
            fairMap.put(MiscUtils.generateFairKey(fair.getFromStation().getId(), fair.getToStation().getId(), fair.getSeatClass()), fair);
        }

        log.info("Fair map: {}", fairMap.keySet());
        return fairMap;
    }
}
