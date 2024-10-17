package com.example.railwayticket.service.impl;

import com.example.railwayticket.exception.ResourceNotFoundException;
import com.example.railwayticket.exception.RuleViolationException;
import com.example.railwayticket.model.dto.request.fare.FareCreateRequest;
import com.example.railwayticket.model.dto.request.fare.FareUpdateRequest;
import com.example.railwayticket.model.dto.response.FareResponse;
import com.example.railwayticket.model.entity.Fare;
import com.example.railwayticket.model.entity.Station;
import com.example.railwayticket.model.enumeration.TicketClass;
import com.example.railwayticket.repository.FareRepository;
import com.example.railwayticket.service.intface.FareService;
import com.example.railwayticket.service.intface.StationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FareServiceImpl implements FareService {

    private final FareRepository fareRepository;
    private final StationService stationService;

    @Override
    public List<FareResponse> getAllFares() {
        return fareRepository.getAllFairs()
                .stream()
                .map(f -> new FareResponse(f, f.getFromStation(), f.getToStation()))
                .toList();
    }

    @Override
    public FareResponse createNewFare(FareCreateRequest request) {
        Fare fare = new Fare();

        return fillFareInformation(fare, request.fromStationId(), request.toStationId(), request.ticketClass(), request.fareAmount());
    }

    @Override
    public FareResponse updateFare(FareUpdateRequest request) {
        Fare fare = fareRepository.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException("FARE_NOT_FOUND", String.format("No Fare found with id %d", request.id())));

        return fillFareInformation(fare, request.fromStationId(), request.toStationId(), request.ticketClass(), request.fareAmount());
    }

    private FareResponse fillFareInformation(Fare fare, Long fromStationId, Long toStationId, TicketClass ticketClass, Double fareAmount) {
        if (fromStationId.equals(toStationId)) {
            throw new RuleViolationException("SAME_START_AND_END_STATION", "Start station & End station must be different to create a Fare");
        }

        long minId = Math.min(fromStationId, toStationId);
        long maxId = Math.max(fromStationId, toStationId);
        Station fromStation = stationService.getStationById(minId);
        Station toStation = stationService.getStationById(maxId);

        fare.setFromStation(fromStation);
        fare.setToStation(toStation);
        fare.setTicketClass(ticketClass);
        fare.setAmount(fareAmount);

        return new FareResponse(fareRepository.save(fare), fromStation, toStation);
    }
}
