package com.example.railwayticket.repository;

import com.example.railwayticket.model.entity.TrainRoute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainRouteRepository extends JpaRepository<TrainRoute, Long> {
    @EntityGraph(attributePaths = {"train.coaches.seats", "trainRouteStations.station"})
    Page<TrainRoute> findTrainRoutesByOrderById(Pageable pageable);

    @EntityGraph(attributePaths = {"train", "route.startStation", "route.endStation"})
    List<TrainRoute> findAllByOrderById();
}
