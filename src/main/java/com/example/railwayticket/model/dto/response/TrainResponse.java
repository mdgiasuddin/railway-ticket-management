package com.example.railwayticket.model.dto.response;

import com.example.railwayticket.model.entity.Train;
import lombok.Getter;

@Getter
public class TrainResponse {
    private final long id;
    private final String name;
    private final boolean active;

    public TrainResponse(Train train) {
        this.id = train.getId();
        this.name = train.getName();
        this.active = train.isActive();
    }
}
