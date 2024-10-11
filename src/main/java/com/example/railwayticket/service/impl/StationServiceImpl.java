package com.example.railwayticket.service.impl;

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
    public List<StationResponse> getAllStations() {
        return convertToResponse(stationRepository.findAll());
    }

    private List<StationResponse> convertToResponse(List<Station> stations) {
        return stations.stream()
                .map(StationResponse::new)
                .toList();
    }
}
