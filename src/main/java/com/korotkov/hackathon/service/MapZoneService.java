package com.korotkov.hackathon.service;

import com.korotkov.hackathon.entity.MapZoneEntity;
import com.korotkov.hackathon.repository.MapZoneRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MapZoneService {
    MapZoneRepository mapZoneRepository;

    @Transactional
    public Mono<MapZoneEntity> save(MapZoneEntity mapZone) {
        mapZone.setAllowed(true);
        return mapZoneRepository.save(mapZone);
    }

    @Transactional
    public Mono<MapZoneEntity> save(MapZoneEntity mapZone, boolean isAllowed) {
        mapZone.setAllowed(isAllowed);
        return mapZoneRepository.save(mapZone);
    }


    public Flux<MapZoneEntity> findAllBanZones() {
        return mapZoneRepository.findByAllowedFalse();
    }

    public Flux<MapZoneEntity> findAll() {
        return mapZoneRepository.findAll();
    }

}
