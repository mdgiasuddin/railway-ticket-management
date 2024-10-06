package com.example.railwayticket.controller;

import com.example.railwayticket.model.dto.request.train.TrainCreateRequest;
import com.example.railwayticket.model.dto.request.train.TrainUpdateRequest;
import com.example.railwayticket.model.dto.response.TrainResponse;
import com.example.railwayticket.service.intface.TrainService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trains")
@RequiredArgsConstructor
public class TrainController {

    private final TrainService trainService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public List<TrainResponse> getAllTrains() {
        return trainService.getAllTrains();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public TrainResponse createTrain(@RequestBody @Valid TrainCreateRequest request) {
        return trainService.createTrain(request);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping
    public TrainResponse updateTrain(@RequestBody @Valid TrainUpdateRequest request) {
        return trainService.updateTrain(request);
    }
}
