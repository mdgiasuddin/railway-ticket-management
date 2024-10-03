package com.example.railwayticket.service.impl;

import com.example.railwayticket.model.dto.response.TicketSearchResponse;
import com.example.railwayticket.service.intface.TicketBookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketBookingServiceImpl implements TicketBookingService {

    @Override
    public TicketSearchResponse searchTicket(long fromStationId, long toStationId, LocalDate date) {
        log.info("From station: {}, to station: {}, date: {}", fromStationId, toStationId, date);
        return null;
    }
}
