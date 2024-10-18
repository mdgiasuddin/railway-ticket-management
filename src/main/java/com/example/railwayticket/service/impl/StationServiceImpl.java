package com.example.railwayticket.service.impl;

import com.example.railwayticket.exception.ResourceNotFoundException;
import com.example.railwayticket.model.dto.request.station.StationCreateRequest;
import com.example.railwayticket.model.dto.request.station.StationUpdateRequest;
import com.example.railwayticket.model.dto.response.StationResponse;
import com.example.railwayticket.model.entity.Station;
import com.example.railwayticket.repository.StationRepository;
import com.example.railwayticket.service.intface.StationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService {
    private final StationRepository stationRepository;

    @Override
    public Station getStationById(long stationId) {
        return stationRepository.findById(stationId)
                .orElseThrow(() -> new ResourceNotFoundException("STATION_NOT_FOUND", String.format("No station found with id: %d", stationId)));
    }

    @Override
    public List<StationResponse> getAllStations() {
        return convertToResponse(stationRepository.findAll());
    }

    @Override
    public StationResponse createNewStation(StationCreateRequest request) {
        Station station = new Station();
        station.setName(request.name());
        station.setDescription(request.description());

        return new StationResponse(stationRepository.save(station));
    }

    @Override
    public StationResponse updateStation(StationUpdateRequest request) {
        Station station = getStationById(request.id());
        station.setName(request.name());
        station.setDescription(request.description());

        return new StationResponse(stationRepository.save(station));
    }

    private List<StationResponse> convertToResponse(List<Station> stations) {
        return stations.stream()
                .map(StationResponse::new)
                .toList();
    }
}
