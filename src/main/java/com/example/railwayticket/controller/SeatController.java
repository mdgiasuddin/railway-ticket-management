package com.example.railwayticket.controller;

import com.example.railwayticket.model.dto.request.seat.SeatCreateRequest;
import com.example.railwayticket.model.dto.request.seat.SeatUpdateRequest;
import com.example.railwayticket.model.dto.response.SeatResponse;
import com.example.railwayticket.service.intface.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @Operation(summary = "Get all Seats of a Coach")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/coach-id/{coachId}")
    public List<SeatResponse> getSeatsByCoachId(@PathVariable long coachId) {
        return seatService.getSeatsByCoachId(coachId);
    }

    @Operation(summary = "Create a new Seat entity in DB")
    @PostMapping
    public SeatResponse createNewSeat(@RequestBody @Valid SeatCreateRequest request) {
        return seatService.createNewSeat(request);
    }

    @Operation(summary = "Update Seat information")
    @PutMapping
    public SeatResponse updateSeat(@RequestBody @Valid SeatUpdateRequest request) {
        return seatService.updateSeat(request);
    }

}
