package com.korotkov.hackathon.repository;


import com.korotkov.hackathon.entity.MapZoneEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MapZoneRepository extends R2dbcRepository<MapZoneEntity, Integer> {
    Mono<MapZoneEntity> findByNorthAndEast(double north, double east);

    Flux<MapZoneEntity> findByAllowedFalse();
}
