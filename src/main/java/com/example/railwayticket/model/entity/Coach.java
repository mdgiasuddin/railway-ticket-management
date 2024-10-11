package com.example.railwayticket.model.entity;

import com.example.railwayticket.model.enumeration.TicketClass;
import com.example.railwayticket.utils.converter.LongListConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table
@Getter
@Setter
public class Coach extends BaseEntity {

    @Column(nullable = false, columnDefinition = "varchar(5)")
    private String name;

    @Column(nullable = false, columnDefinition = "varchar(10)")
    @Enumerated(STRING)
    private TicketClass ticketClass;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private Train train;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int totalSeats = 0;

    @Column(columnDefinition = "varchar(255)")
    @Convert(converter = LongListConverter.class)
    private List<Long> seatOrientation = new ArrayList<>();

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean active = false;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int ordering = 0;

    @OneToMany(mappedBy = "coach", fetch = LAZY)
    private Set<Seat> seats = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coach coach)) return false;
        return getId() == coach.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Coach{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", seatClass=" + ticketClass +
                '}';
    }
}
