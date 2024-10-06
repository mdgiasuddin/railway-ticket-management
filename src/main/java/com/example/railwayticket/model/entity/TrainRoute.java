package com.example.railwayticket.model.entity;

import com.example.railwayticket.model.enumeration.RouteType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table
@Getter
@Setter
public class TrainRoute {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private Train train;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Route route;

    @Column(nullable = false)
    @Enumerated(STRING)
    private DayOfWeek offDay;

    @Column(nullable = false, columnDefinition = "varchar(4)")
    @Enumerated(STRING)
    private RouteType routeType;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int dayFromStartStation = 0;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "trainRoute", fetch = LAZY)
    private Set<TrainRouteStation> trainRouteStations;
}
