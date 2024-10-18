package com.example.railwayticket.service.impl;

import com.example.railwayticket.model.entity.Coach;
import com.example.railwayticket.model.entity.Fare;
import com.example.railwayticket.model.entity.Seat;
import com.example.railwayticket.model.entity.TrainJourney;
import com.example.railwayticket.model.entity.TrainRoute;
import com.example.railwayticket.model.entity.TrainRouteStation;
import com.example.railwayticket.repository.FareRepository;
import com.example.railwayticket.repository.TrainJourneyRepository;
import com.example.railwayticket.repository.TrainRouteRepository;
import com.example.railwayticket.utils.AppDateTimeUtils;
import com.example.railwayticket.utils.MiscUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
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
import static com.example.railwayticket.utils.AppDateTimeUtils.BD_TIME_ZONE;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SchedulerService {

    private final TrainRouteRepository trainRouteRepository;
    private final FareRepository fareRepository;
    private final TrainJourneyRepository trainJourneyRepository;

    @Transactional
    @Scheduled(cron = "${train-journey.scheduler}", zone = BD_TIME_ZONE)
    public void scheduleTrainJourney() {
        log.info("Scheduler started at: {}", AppDateTimeUtils.nowInBD());
        LocalDate journeyDate = AppDateTimeUtils.todayInBD().plusDays(1);

        int page = 0;
        int routeFetchSize = 100;
        Page<TrainRoute> trainRoutePage;

        do {
            Pageable pageable = PageRequest.of(page, routeFetchSize);
            trainRoutePage = trainRouteRepository.findTrainRoutesByOrderById(pageable);

            for (TrainRoute trainRoute : trainRoutePage) {
                if (trainRoute.getTrain().isActive() && !journeyDate.getDayOfWeek().equals(trainRoute.getOffDay())) {
                    createTrainJourney(trainRoute, journeyDate);
                }
            }
            page += 1;
        } while (trainRoutePage.hasNext());
    }

    private void createTrainJourney(TrainRoute trainRoute, LocalDate journeyDate) {
        Set<TrainRouteStation> trainRouteStations = trainRoute.getTrainRouteStations();
        Map<Long, TrainRouteStation> stationMap = buildStationMap(trainRouteStations);
        List<Long> stationIds = getStationIds(trainRouteStations);
        List<Fare> fares = fareRepository.getAllRouteFairs(stationIds);
        Map<String, Fare> fareMap = buildFairMap(fares);

        List<TrainJourney> trainJourneys = new ArrayList<>();
        for (Coach coach : trainRoute.getTrain().getCoaches()) {
            if (!coach.isActive())
                continue;

            for (Seat seat : coach.getSeats()) {
                List<Long> seatStationMapping = UP.equals(trainRoute.getRouteType()) ? seat.getUpStationMapping() : seat.getDownStationMapping();
                log.info("Seat: {}, Station mapping: {}", seat.getId(), seatStationMapping);
                for (int i = 0; i < seatStationMapping.size() - 1; i++) {
                    long fromStationId = seatStationMapping.get(i);
                    long toStationId = seatStationMapping.get(i + 1);

                    log.info("Seat: {}, From station {}, to station {}, class {}", seat.getId(), fromStationId, toStationId, coach.getTicketClass());

                    TrainJourney trainJourney = new TrainJourney();
                    trainJourney.setIdKey(MiscUtils.generateIdKey());
                    trainJourney.setFromStation(stationMap.get(fromStationId).getStation());
                    trainJourney.setToStation(stationMap.get(toStationId).getStation());
                    trainJourney.setSeat(seat);
                    trainJourney.setTrainRoute(trainRoute);
                    trainJourney.setFare(fareMap.get(MiscUtils.generateFareKey(fromStationId, toStationId, coach.getTicketClass()))
                            .getAmount());
                    trainJourney.setSeatStatus(AVAILABLE);
                    LocalDateTime journeyDateTime = LocalDateTime.of(journeyDate, trainRoute.getStartTime())
                            .plusMinutes(stationMap.get(fromStationId).getTimeFromStartStation());
                    trainJourney.setJourneyDate(journeyDateTime.toLocalDate());
                    trainJourney.setJourneyTime(journeyDateTime.toLocalTime());
                    trainJourney.setDestinationArrivalTime(LocalDateTime.of(journeyDate, trainRoute.getStartTime())
                            .plusMinutes(stationMap.get(toStationId).getTimeFromStartStation()));
                    trainJourneys.add(trainJourney);
                }
            }
        }

        trainJourneyRepository.saveAll(trainJourneys);
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
            fairMap.put(MiscUtils.generateFareKey(fare.getFromStation().getId(), fare.getToStation().getId(), fare.getTicketClass()), fare);
        }

        return fairMap;
    }
}
