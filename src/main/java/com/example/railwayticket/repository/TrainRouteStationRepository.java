package com.example.railwayticket.repository;

import com.example.railwayticket.model.entity.TrainRouteStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrainRouteStationRepository extends JpaRepository<TrainRouteStation, Long> {
    @Query("select trs from TrainRouteStation trs inner join trs.trainRoute tr where tr.id = :trainRouteId order by trs.timeFromStartStation")
    List<TrainRouteStation> getStationsOfTrainRoute(long trainRouteId);
}
