package com.example.railwayticket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SchedulerService {

    @Scheduled(initialDelay = 1000)
    public void schedule() {
        log.info("Scheduler started at: {}", LocalDateTime.now());
    }
}
