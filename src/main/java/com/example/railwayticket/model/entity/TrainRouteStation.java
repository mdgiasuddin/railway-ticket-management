package com.example.railwayticket.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table
@Getter
@Setter
public class TrainRouteStation extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private TrainRoute trainRoute;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private Station station;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int timeFromStartStation = 0;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrainRouteStation that)) return false;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
