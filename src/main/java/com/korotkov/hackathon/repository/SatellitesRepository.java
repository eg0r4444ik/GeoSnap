package com.korotkov.hackathon.repository;

import com.korotkov.hackathon.entity.SatelliteEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SatellitesRepository extends R2dbcRepository<SatelliteEntity, Integer> {
    Mono<SatelliteEntity> findByName(String name);
}
