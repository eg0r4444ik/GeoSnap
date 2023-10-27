package com.korotkov.hackathon.repository;


import com.korotkov.hackathon.entity.MapZoneEntity;
import com.korotkov.hackathon.entity.SatelliteEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapZoneRepository  extends R2dbcRepository<MapZoneEntity, Integer> {
}
