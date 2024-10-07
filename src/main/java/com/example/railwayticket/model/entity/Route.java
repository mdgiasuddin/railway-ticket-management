package com.example.railwayticket.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table
@Getter
@Setter
public class Route extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private Station startStation;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private Station endStation;

    @Override
    public String toString() {
        return "Route{" +
                "id=" + getId() +
                ", startStation=" + startStation +
                ", endStation=" + endStation +
                '}';
    }
}
