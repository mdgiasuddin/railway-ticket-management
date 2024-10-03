package com.example.railwayticket.utils;

import com.example.railwayticket.model.enums.SeatClass;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class MiscUtils {

    public static String generateFairKey(long fromStationId, long toStationId, SeatClass seatClass) {
        long firstStationId = Math.min(fromStationId, toStationId);
        long lastStationId = Math.max(fromStationId, toStationId);

        return String.format("%d:%d:%s", firstStationId, lastStationId, seatClass.name());
    }
}
