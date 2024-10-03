package com.example.railwayticket.utils;

import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class AppDateTimeUtils {
    public static final String BD_TIME_ZONE = "Asia/Dhaka";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd hh:mm:ss a";

    public static LocalDate todayInBD() {
        return LocalDate.now(ZoneId.of(BD_TIME_ZONE));
    }

}
