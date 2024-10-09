package com.example.railwayticket.utils;

import com.example.railwayticket.model.enumeration.TicketClass;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

@NoArgsConstructor(access = PRIVATE)
public class MiscUtils {

    public static String generateFareKey(long fromStationId, long toStationId, TicketClass ticketClass) {
        long firstStationId = Math.min(fromStationId, toStationId);
        long lastStationId = Math.max(fromStationId, toStationId);

        return String.format("%d:%d:%s", firstStationId, lastStationId, ticketClass.name());
    }

    public static String generateIdKey() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "");
    }

    public static ResponseEntity<Resource> convertToFile(Resource resource, String filename) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MULTIPART_FORM_DATA);
        httpHeaders.add("content-disposition", "attachment; filename=" + filename);
        return new ResponseEntity<>(resource, httpHeaders, OK);
    }
}
