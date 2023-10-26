package com.korotkov.hackathon.repository;

import com.korotkov.hackathon.entity.SatelliteEntity;
import com.korotkov.hackathon.entity.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SatellitesRepository extends R2dbcRepository<SatelliteEntity, Integer> {
}
