package com.example.railwayticket.repository;

import com.example.railwayticket.model.entity.Fare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FairRepository extends JpaRepository<Fare, Long> {

    @Query("""
            select f from Fare f inner join f.fromStation fs inner join f.toStation ts
            where fs.id in :stationIds and ts.id in :stationIds
            """)
    List<Fare> getAllRouteFairs(@Param("stationIds") List<Long> stationIds);
}
