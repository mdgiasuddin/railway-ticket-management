package com.example.railwayticket.repository;

import com.example.railwayticket.model.entity.Route;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route, Long> {

    @EntityGraph(attributePaths = {"startStation", "endStation"})
    List<Route> findAllByOrderById();

    @EntityGraph(attributePaths = {"startStation", "endStation"})
    Optional<Route> findRouteWithStationsById(Long id);

    @Query("""
            select r from Route r inner join r.startStation ss inner join r.endStation es
            where (ss.id = :startStationId and es.id = :endStationId) or
            (es.id = :startStationId and ss.id = :endStationId)
            """)
    Optional<Route> getRouteByStationIds(long startStationId, long endStationId);
}
