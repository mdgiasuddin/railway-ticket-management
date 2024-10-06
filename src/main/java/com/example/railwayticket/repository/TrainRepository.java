package com.example.railwayticket.repository;

import com.example.railwayticket.model.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainRepository extends JpaRepository<Train, Long> {
}
