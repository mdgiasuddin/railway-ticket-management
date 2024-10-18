package com.example.railwayticket.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table
@Getter
@Setter
public class Station extends BaseEntity {

    @Column(nullable = false, unique = true, columnDefinition = "varchar(50)")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Station station)) return false;
        return getId() == station.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
