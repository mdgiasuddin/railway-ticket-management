package com.example.railwayticket.repository;

import com.example.railwayticket.model.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    @Query("select s from Seat s inner join s.coach c where c.id = :coachId order by s.ordering")
    List<Seat> getSeatsByCoachId(long coachId);
}
