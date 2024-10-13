package com.example.railwayticket.service.intface;

import com.example.railwayticket.model.dto.request.seat.SeatCreateRequest;
import com.example.railwayticket.model.dto.request.seat.SeatUpdateRequest;
import com.example.railwayticket.model.dto.response.SeatResponse;

import java.util.List;

public interface SeatService {
    List<SeatResponse> getSeatsByCoachId(long coachId);

    SeatResponse createNewSeat(SeatCreateRequest request);

    SeatResponse updateSeat(SeatUpdateRequest request);
}
