-- unique constraints
alter table coach
    add unique (train_id, name);
alter table seat
    add unique (coach_id, number);
alter table fare
    add unique (from_station_id, to_station_id, ticket_class);
alter table train_route
    add unique (train_id, route_type);
alter table train_route_station
    add unique (station_id, train_route_id);
alter table seat_for_journey
    add unique (journey_date, from_station_id, to_station_id, seat_id);

-- index
create index idx_seat_coach_id on seat (coach_id);
create index idx_coach_train_id on coach (train_id);
create index idx_seat_for_journey_journey_date_station on seat_for_journey (journey_date, from_station_id, to_station_id);