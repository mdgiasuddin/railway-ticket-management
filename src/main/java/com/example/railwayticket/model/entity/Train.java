package com.example.railwayticket.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table
@Getter
@Setter
public class Train extends BaseEntity {

    @Column(nullable = false, unique = true, columnDefinition = "varchar(50)")
    private String name;

    @OneToMany(mappedBy = "train", fetch = LAZY)
    private Set<Coach> coaches;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean active = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Train train)) return false;
        return getId() == train.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Train{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                '}';
    }
}
