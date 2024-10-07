package com.example.railwayticket.model.entity;

import com.example.railwayticket.utils.converter.LongListConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table
@Getter
@Setter
public class Seat extends BaseEntity {

    @Column(nullable = false, columnDefinition = "varchar(5)")
    private String number;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private Coach coach;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int ordering = 0;

    @Column(columnDefinition = "varchar(255)")
    @Convert(converter = LongListConverter.class)
    private List<Long> upStationMapping = new ArrayList<>();

    @Column(columnDefinition = "varchar(255)")
    @Convert(converter = LongListConverter.class)
    private List<Long> downStationMapping = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seat seat)) return false;
        return getId() == seat.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + getId() +
                ", number='" + number + '\'' +
                ", ordering=" + ordering +
                '}';
    }
}
