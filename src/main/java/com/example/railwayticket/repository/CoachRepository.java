package com.example.railwayticket.repository;

import com.example.railwayticket.model.entity.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CoachRepository extends JpaRepository<Coach, Long> {

    @Query("select c from Coach c inner join c.train t where t.id = :trainId")
    List<Coach> getCoachByTrainId(long trainId);
}
