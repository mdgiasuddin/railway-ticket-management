package com.example.railwayticket.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table
@Getter
@Setter
public class TrainRouteStation {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private TrainRoute trainRoute;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private Station station;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int timeFromStartStation = 0;
}
