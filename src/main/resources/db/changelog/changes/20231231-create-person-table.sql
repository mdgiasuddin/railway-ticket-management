--liquibase formatted sql
--changeset giash:20230709
--comment: Create person table with name & nationality
create table if not exists person
(
    id          bigserial,
    name        varchar(255) not null,
    nationality varchar(255) not null,
    constraint pk_person primary key (id)
);

comment on column person.id is 'Auto incremented id';
comment on column person.name is 'Person name will be stored';
comment on column person.nationality is 'Person country of birth';
comment on table person is 'Will be used to store details of a person';