package com.example.railwayticket.repository;

import com.example.railwayticket.model.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, Long> {
}
