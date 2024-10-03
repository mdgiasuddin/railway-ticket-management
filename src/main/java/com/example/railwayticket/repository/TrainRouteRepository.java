package com.example.railwayticket.repository;

import com.example.railwayticket.model.entity.TrainRoute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainRouteRepository extends JpaRepository<TrainRoute, Long> {
    @EntityGraph(attributePaths = {"train.coaches.seats", "trainRouteStations.station"})
    Page<TrainRoute> findTrainRoutesByIdIsNotNull(Pageable pageable);
}
