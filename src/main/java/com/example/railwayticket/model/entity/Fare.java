package com.example.railwayticket.model.entity;

import com.example.railwayticket.model.enumeration.SeatClass;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table
@Getter
@Setter
public class Fare extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private Station fromStation;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private Station toStation;

    @Column(nullable = false, columnDefinition = "varchar(10)")
    @Enumerated(STRING)
    private SeatClass seatClass;

    @Column(nullable = false)
    private Double amount;

    @Override
    public String toString() {
        return "Fair{" +
                "id=" + getId() +
                ", fromStation=" + fromStation +
                ", toStation=" + toStation +
                ", seatClass=" + seatClass +
                ", amount=" + amount +
                '}';
    }
}
