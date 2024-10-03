package com.example.railwayticket.model.entity;

import com.example.railwayticket.model.enums.SeatClass;
import com.example.railwayticket.utils.converter.LongListConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table
@Getter
@Setter
public class Coach {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Column(nullable = false, columnDefinition = "varchar(5)")
    private String name;

    @Column(nullable = false, columnDefinition = "varchar(10)")
    @Enumerated(STRING)
    private SeatClass seatClass;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private Train train;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer totalSeats = 0;

    @Column(columnDefinition = "varchar(255)")
    @Convert(converter = LongListConverter.class)
    private List<Long> seatOrientation = new ArrayList<>();

    @OneToMany(mappedBy = "coach", fetch = LAZY)
    private Set<Seat> seats = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coach coach)) return false;
        return id == coach.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
