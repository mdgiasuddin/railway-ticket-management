package com.example.railwayticket.model.entity;

import com.example.railwayticket.model.enumeration.TicketClass;
import com.example.railwayticket.utils.converter.LongListConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Table
@Getter
@Setter
public class Ticket extends BaseEntity {
    @Column(nullable = false, columnDefinition = "varchar(32)")
    private String idKey;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    @Convert(converter = LongListConverter.class)
    private List<Long> journeyIds = new ArrayList<>();

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String trainName;

    @Column(nullable = false, columnDefinition = "varchar(10)")
    @Enumerated(STRING)
    private TicketClass ticketClass;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String seats;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String fromStation;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String toStation;

    @Column(nullable = false)
    private LocalDateTime journeyDate;

    @Column(nullable = false)
    private Double fare;

    @Column(nullable = false)
    private Double serviceCharge;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String passengerName;

    @Column(nullable = false, columnDefinition = "varchar(15)")
    private String passengerMobileNumber;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String passengerEmail;

    @Column(nullable = false, columnDefinition = "varchar(20)")
    private String passengerNid;

    @Column(nullable = false, columnDefinition = "varchar(36)")
    private String filename;
}
