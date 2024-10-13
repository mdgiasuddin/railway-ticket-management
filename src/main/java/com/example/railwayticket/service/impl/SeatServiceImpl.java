package com.example.railwayticket.service.impl;

import com.example.railwayticket.exception.ResourceNotFoundException;
import com.example.railwayticket.exception.RuleViolationException;
import com.example.railwayticket.model.dto.request.seat.SeatCreateRequest;
import com.example.railwayticket.model.dto.request.seat.SeatUpdateRequest;
import com.example.railwayticket.model.dto.response.SeatResponse;
import com.example.railwayticket.model.dto.response.StationResponse;
import com.example.railwayticket.model.entity.Coach;
import com.example.railwayticket.model.entity.Seat;
import com.example.railwayticket.model.entity.Station;
import com.example.railwayticket.model.enumeration.RouteType;
import com.example.railwayticket.repository.SeatRepository;
import com.example.railwayticket.repository.StationRepository;
import com.example.railwayticket.service.intface.CoachService;
import com.example.railwayticket.service.intface.SeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.railwayticket.model.enumeration.RouteType.DOWN;
import static com.example.railwayticket.model.enumeration.RouteType.UP;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {
    private final SeatRepository seatRepository;
    private final CoachService coachService;
    private final StationRepository stationRepository;

    @Override
    public List<SeatResponse> getSeatsByCoachId(long coachId) {
        return seatRepository.getSeatsByCoachId(coachId)
                .stream()
                .map(SeatResponse::new)
                .toList();
    }

    @Override
    public SeatResponse createNewSeat(SeatCreateRequest request) {
        Seat seat = new Seat();
        return fillSeatInformation(seat, request.coachId(), request.number(), request.ordering(), request.upStationIds(), request.downStationIds());
    }

    @Override
    public SeatResponse updateSeat(SeatUpdateRequest request) {
        Seat seat = getSeatById(request.id());
        return fillSeatInformation(seat, request.coachId(), request.number(), request.ordering(), request.upStationIds(), request.downStationIds());
    }


    private Seat getSeatById(long seatId) {
        return seatRepository.findById(seatId)
                .orElseThrow(() -> new ResourceNotFoundException("SEAT_NOT_FOUND", String.format("No seat found with id %d", seatId)));
    }

    private SeatResponse fillSeatInformation(Seat seat, Long coachId, String seatNumber, Integer ordering,
                                             List<Long> upStationIds, List<Long> downStationIds) {

        Coach coach = coachService.getCoachById(coachId);

        seat.setNumber(seatNumber);
        seat.setOrdering(ordering);
        seat.setCoach(coach);

        List<StationResponse> upStations = updateStationMapping(seat, coach.getId(), upStationIds, UP);
        List<StationResponse> downStations = updateStationMapping(seat, coach.getId(), downStationIds, DOWN);

        return new SeatResponse(seatRepository.save(seat), coach, upStations, downStations);
    }

    private List<StationResponse> updateStationMapping(Seat seat, Long coachId, List<Long> stationIds, RouteType routeType) {
        if (stationIds.isEmpty()) {
            return new ArrayList<>();
        }

        List<Station> stations = stationRepository.getListOfStationsForSeatMapping(stationIds, coachId, routeType);
        if (stations.size() != stationIds.size()) {
            throw new RuleViolationException("MAPPING_STATION_NOT_FOUND", String.format("Invalid id of %s Station mapping", routeType));
        }

        log.info("{} stations: {}", routeType, stations);
        List<Long> orderedStationIds = stations.stream()
                .map(Station::getId)
                .toList();
        if (UP.equals(routeType)) {
            seat.setUpStationMapping(orderedStationIds);
        } else {
            seat.setDownStationMapping(orderedStationIds);
        }

        return stations.stream()
                .map(StationResponse::new)
                .toList();
    }

}
