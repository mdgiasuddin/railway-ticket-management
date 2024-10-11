package com.example.railwayticket.model.dto.response;

import com.example.railwayticket.model.entity.Station;
import lombok.Getter;

@Getter
public class StationResponse {
    private final long id;
    private final String name;
    private final String description;

    public StationResponse(Station station) {
        this.id = station.getId();
        this.name = station.getName();
        this.description = station.getDescription();
    }
}
