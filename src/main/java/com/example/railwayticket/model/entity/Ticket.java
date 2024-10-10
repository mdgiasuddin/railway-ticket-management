package com.example.railwayticket.model.entity;

import com.example.railwayticket.utils.converter.LongListConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Ticket extends BaseEntity {
    @Column(nullable = false, columnDefinition = "varchar(32)")
    private String idKey;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    @Convert(converter = LongListConverter.class)
    private List<Long> seatIds = new ArrayList<>();

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String seats;
}
