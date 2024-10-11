package com.example.railwayticket.model.entity;

import com.example.railwayticket.utils.converter.LongListConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false, columnDefinition = "varchar(20)")
    private String passengerNid;
}
