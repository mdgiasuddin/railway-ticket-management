package com.example.railwayticket.repository;

import com.example.railwayticket.model.entity.Station;
import com.example.railwayticket.model.enumeration.RouteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StationRepository extends JpaRepository<Station, Long> {

    @Query("select s from TrainRouteStation trs inner join trs.station s inner join trs.trainRoute tr inner join tr.train t inner join t.coaches c where s.id in :stationIds and tr.routeType = :routeType and c.id = :coachId order by trs.timeFromStartStation")
    List<Station> getListOfStationsForSeatMapping(List<Long> stationIds, long coachId, RouteType routeType);

}
