package com.example.railwayticket.repository;

import com.example.railwayticket.model.entity.Fare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FareRepository extends JpaRepository<Fare, Long> {

    @Query("""
            select f from Fare f inner join fetch f.fromStation fs inner join fetch f.toStation ts
            order by fs.id, ts.id, f.ticketClass
            """)
    List<Fare> getAllRouteFairs();

    @Query("""
            select f from Fare f inner join fetch f.fromStation fs inner join fetch f.toStation ts
            where fs.id in :stationIds and ts.id in :stationIds
            """)
    List<Fare> getAllRouteFairs(@Param("stationIds") List<Long> stationIds);
}
