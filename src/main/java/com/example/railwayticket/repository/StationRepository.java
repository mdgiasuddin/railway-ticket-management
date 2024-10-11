package com.example.railwayticket.repository;

import com.example.railwayticket.model.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long> {
}
