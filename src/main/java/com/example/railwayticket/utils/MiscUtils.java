package com.example.railwayticket.utils;

import com.example.railwayticket.model.enums.SeatClass;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class MiscUtils {

    public static String generateFareKey(long fromStationId, long toStationId, SeatClass seatClass) {
        long firstStationId = Math.min(fromStationId, toStationId);
        long lastStationId = Math.max(fromStationId, toStationId);

        return String.format("%d:%d:%s", firstStationId, lastStationId, seatClass.name());
    }

    public static String generateIdKey() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "");
    }
}
