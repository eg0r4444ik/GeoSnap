CREATE TABLE IF NOT EXISTS  users
(
    id       integer generated by default as identity
        primary key,
    username varchar(64),
    password varchar(512),
    role     varchar(32),
    enabled  boolean
);

CREATE TABLE IF NOT EXISTS satellites
(
    id                   bigint generated by default as identity
        constraint satellite_pkey
            primary key,
    orbit_period         bigint           not null,
    earth_to_orbit_angle double precision not null,
    distance_to_earth    double precision not null,
    view_angle           double precision not null,
    time_start           bigint           not null
);


CREATE TABLE IF NOT EXISTS map_zones
(
    id    integer generated by default as identity
        primary key,
    north double precision,
    south double precision,
    east  double precision,
    west  double precision
);

