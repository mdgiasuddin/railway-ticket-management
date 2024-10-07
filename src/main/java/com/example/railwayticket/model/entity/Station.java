package com.example.railwayticket.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Station extends BaseEntity {

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String name;

    @Column(nullable = false)
    private String description;

    @Override
    public String toString() {
        return "Station{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                '}';
    }
}
