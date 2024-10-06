package com.example.railwayticket.repository;

import com.example.railwayticket.model.entity.SeatForJourney;
import com.example.railwayticket.model.enumeration.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SeatForJourneyRepository extends JpaRepository<SeatForJourney, Long> {

    @Query("""
            select sfj from SeatForJourney sfj inner join fetch sfj.seat s inner join fetch s.coach c inner join fetch c.train t
            inner join fetch sfj.trainRoute tr inner join fetch tr.route r inner join fetch sfj.fromStation fs inner join fetch sfj.toStation ts
            where sfj.journeyDate = :journeyDate and fs.id = :fromStationId and ts.id = :toStationId and (sfj.seatStatus = :seatStatus1 or
            (sfj.seatStatus = :seatStatus2 and sfj.bookingTime < :bookingExpiry))
            """)
    List<SeatForJourney> searchSeatForJourney(
            long fromStationId, long toStationId, LocalDate journeyDate, SeatStatus seatStatus1,
            SeatStatus seatStatus2, LocalDateTime bookingExpiry
    );
}
