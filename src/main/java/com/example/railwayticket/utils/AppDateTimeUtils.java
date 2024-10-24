package com.example.railwayticket.utils;

import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class AppDateTimeUtils {
    public static final String BD_TIME_ZONE = "Asia/Dhaka";
    public static final String DATE_PATTERN = "dd MMM yyyy";
    public static final String DATE_PATTERN_FILE_DIR = "yyyyMMdd";
    public static final String DATE_TIME_PATTERN = "dd MMM yyyy, hh:mm a";

    public static LocalDate todayInBD() {
        return LocalDate.now(ZoneId.of(BD_TIME_ZONE));
    }

    public static LocalDateTime nowInBD() {
        return LocalDateTime.now(ZoneId.of(BD_TIME_ZONE));
    }

}
