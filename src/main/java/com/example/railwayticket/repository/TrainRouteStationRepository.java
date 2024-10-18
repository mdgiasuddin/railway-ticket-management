package com.example.railwayticket.repository;

import com.example.railwayticket.model.entity.Station;
import com.example.railwayticket.model.entity.TrainRouteStation;
import com.example.railwayticket.model.enumeration.RouteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrainRouteStationRepository extends JpaRepository<TrainRouteStation, Long> {
    @Query("""
            select trs from TrainRouteStation trs inner join trs.trainRoute tr inner join fetch trs.station s
            where tr.id = :trainRouteId order by trs.timeFromStartStation
            """)
    List<TrainRouteStation> getStationsOfTrainRoute(long trainRouteId);

    @Query("""
            select trs.station from TrainRouteStation trs inner join trs.trainRoute tr inner join tr.train t
            where t.id = :trainId and tr.routeType = :routeType order by trs.timeFromStartStation
            """)
    List<Station> getStationsOfTrainRoute(long trainId, RouteType routeType);
}
