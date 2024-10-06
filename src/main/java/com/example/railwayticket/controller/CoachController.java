package com.example.railwayticket.controller;

import com.example.railwayticket.model.dto.request.coach.CoachCreateRequest;
import com.example.railwayticket.model.dto.response.CoachResponse;
import com.example.railwayticket.service.intface.CoachService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/coaches")
@RequiredArgsConstructor
public class CoachController {

    private final CoachService coachService;

    @Operation(summary = "Create a new Coach entity in the DB")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public CoachResponse createNewCoach(@RequestBody @Valid CoachCreateRequest request) {
        return coachService.createNewCoach(request);
    }

    @Operation(summary = "Get list of Coaches of a Train")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/train-id/{trainId}")
    public List<CoachResponse> getCoachByTrainId(@PathVariable int trainId) {
        return coachService.getCoachByTrainId(trainId);
    }
}
