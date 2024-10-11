package com.example.railwayticket.constant;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ExceptionCode {
    public static final String TRAIN_NOT_FOUND = "TRAIN_NOT_FOUND";
    public static final String COACH_NOT_FOUND = "COACH_NOT_FOUND";
}
