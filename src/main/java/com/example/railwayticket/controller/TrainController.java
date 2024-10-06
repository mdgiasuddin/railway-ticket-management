package com.example.railwayticket.controller;

import com.example.railwayticket.model.dto.request.train.TrainCreateRequest;
import com.example.railwayticket.model.dto.request.train.TrainStatusUpdateRequest;
import com.example.railwayticket.model.dto.request.train.TrainUpdateRequest;
import com.example.railwayticket.model.dto.response.TrainResponse;
import com.example.railwayticket.service.intface.TrainService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    @Operation(summary = "Get list of all Trains")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public List<TrainResponse> getAllTrains() {
        return trainService.getAllTrains();
    }

    @Operation(summary = "Create a new Train entity in the DB")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public TrainResponse createTrain(@RequestBody @Valid TrainCreateRequest request) {
        return trainService.createTrain(request);
    }

    @Operation(summary = "Update Train information")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping
    public TrainResponse updateTrain(@RequestBody @Valid TrainUpdateRequest request) {
        return trainService.updateTrain(request);
    }

    @Operation(summary = "Update Train status: active/inactive")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("/status")
    public TrainResponse updateTrainStatus(@RequestBody @Valid TrainStatusUpdateRequest request) {
        return trainService.updateTrainStatus(request);
    }
}
