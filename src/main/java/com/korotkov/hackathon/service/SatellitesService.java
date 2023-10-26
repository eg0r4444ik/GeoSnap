package com.korotkov.hackathon.service;

import com.korotkov.hackathon.entity.SatelliteEntity;
import com.korotkov.hackathon.repository.SatellitesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SatellitesService {

    private final SatellitesRepository satellitesRepository;

    @Autowired
    public SatellitesService(SatellitesRepository satellitesRepository) {
        this.satellitesRepository = satellitesRepository;
    }


    public Flux<SatelliteEntity> getSatellites(){
        return satellitesRepository.findAll();
    }

    @Transactional
    public Mono<SatelliteEntity> save(SatelliteEntity satelliteEntity){
        return satellitesRepository.save(satelliteEntity);
    }
}
