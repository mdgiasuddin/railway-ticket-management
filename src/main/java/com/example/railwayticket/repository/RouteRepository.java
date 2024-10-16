package com.example.railwayticket.repository;

import com.example.railwayticket.model.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route, Long> {
    Optional<Route> findRouteWithStationsById(Long id);
}
