package com.example.railwayticket.controller;

import com.example.railwayticket.model.dto.request.fare.FareCreateRequest;
import com.example.railwayticket.model.dto.request.fare.FareUpdateRequest;
import com.example.railwayticket.model.dto.response.FareResponse;
import com.example.railwayticket.service.intface.FareService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/fares")
@RequiredArgsConstructor
public class FareController {

    private final FareService fareService;

    @Operation(summary = "Get all Fares")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public List<FareResponse> getAllFares() {
        return fareService.getAllFares();
    }

    @Operation(summary = "Create a new Fair entity in DB")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public FareResponse createNewFare(@RequestBody @Valid FareCreateRequest request) {
        return fareService.createNewFare(request);
    }

    @Operation(summary = "Update Fair information")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping
    public FareResponse updateFare(@RequestBody @Valid FareUpdateRequest request) {
        return fareService.updateFare(request);
    }
}
