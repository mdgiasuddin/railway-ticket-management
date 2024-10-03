package com.example.railwayticket.repository;

import com.example.railwayticket.model.entity.Fair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FairRepository extends JpaRepository<Fair, Long> {

    @Query("""
            select f from Fair f inner join f.fromStation fs inner join f.toStation ts
            where fs.id in :stationIds and ts.id in :stationIds
            """)
    List<Fair> getAllRouteFairs(@Param("stationIds") List<Long> stationIds);
}
