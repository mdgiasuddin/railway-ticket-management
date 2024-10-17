package com.example.railwayticket.service.intface;

import com.example.railwayticket.model.dto.request.fare.FareCreateRequest;
import com.example.railwayticket.model.dto.request.fare.FareUpdateRequest;
import com.example.railwayticket.model.dto.response.FareResponse;

import java.util.List;

public interface FareService {
    List<FareResponse> getAllFares();

    FareResponse createNewFare(FareCreateRequest request);

    FareResponse updateFare(FareUpdateRequest request);
}
