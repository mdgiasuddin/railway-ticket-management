package com.example.railwayticket.model.entity;

import com.example.railwayticket.model.enums.SeatStatus;
import com.example.railwayticket.utils.AppDateTimeUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.example.railwayticket.constant.AppConstant.BOOKING_VALIDITY;
import static com.example.railwayticket.model.enums.SeatStatus.AVAILABLE;
import static com.example.railwayticket.model.enums.SeatStatus.BOOKED;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table
@Getter
@Setter
public class SeatForJourney {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Column(nullable = false, columnDefinition = "varchar(32)")
    private String idKey;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private Station fromStation;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private Station toStation;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Seat seat;

    @ManyToOne
    @JoinColumn(nullable = false)
    private TrainRoute trainRoute;

    @Column(nullable = false)
    private Double fare;

    @Column(nullable = false, columnDefinition = "varchar(10)")
    @Enumerated(STRING)
    private SeatStatus seatStatus;

    @ManyToOne(fetch = LAZY)
    private User user;

    private LocalDateTime bookingTime;

    @Column(nullable = false)
    private LocalDate journeyDate;

    @Column(nullable = false)
    private LocalTime journeyTime;

    @Column(nullable = false)
    private LocalDateTime destinationArrivalTime;

    @Transient
    public boolean isAvailable() {
        return AVAILABLE.equals(seatStatus) ||
                (BOOKED.equals(seatStatus) &&
                        AppDateTimeUtils.nowInBD().minusMinutes(BOOKING_VALIDITY).isAfter(bookingTime)
                );
    }

    @Override
    public String toString() {
        return "SeatForJourney{" +
                "id=" + id +
                ", seat=" + seat +
                ", fromStation=" + fromStation +
                ", toStation=" + toStation +
                '}';
    }
}
