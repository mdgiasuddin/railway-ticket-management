package com.example.railwayticket.repository;

import com.example.railwayticket.model.entity.TrainJourney;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TrainJourneyRepository extends JpaRepository<TrainJourney, Long> {

    @Query("""
            select tj from TrainJourney tj inner join fetch tj.seat s inner join fetch s.coach c inner join fetch c.train t
            inner join fetch tj.trainRoute tr inner join fetch tr.route r inner join fetch tj.fromStation fs inner join fetch tj.toStation ts
            where tj.journeyDate = :journeyDate and fs.id = :fromStationId and ts.id = :toStationId
            """)
    List<TrainJourney> searchSeatForJourney(
            long fromStationId, long toStationId, LocalDate journeyDate
    );

    @EntityGraph(attributePaths = {"bookedBy", "fromStation", "toStation", "seat.coach"})
    List<TrainJourney> findByIdIn(List<Long> ids);
}
