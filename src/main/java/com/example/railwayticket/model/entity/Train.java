package com.example.railwayticket.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table
@Getter
@Setter
public class Train {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String name;
}
