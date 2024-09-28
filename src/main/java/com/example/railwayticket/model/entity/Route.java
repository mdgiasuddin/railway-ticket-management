package com.example.railwayticket.model.entity;

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
public class Route {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private Station startStation;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private Station endStation;
}
