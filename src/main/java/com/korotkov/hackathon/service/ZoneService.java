package com.korotkov.hackathon.service;

import com.korotkov.hackathon.dto.request.ZoneRequestZone;
import com.korotkov.hackathon.entity.ZoneEntity;
import com.korotkov.hackathon.repository.ZoneRepository;
import com.korotkov.hackathon.util.Zone;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ZoneService {

    CoordsService coordsService;
    ZoneRepository zoneRepository;

    @Transactional
    public Mono<ZoneEntity> save(ZoneEntity zoneEntity) {
        return zoneRepository.save(zoneEntity);
    }


    @Transactional
    public Mono<Zone> save(Zone zone) {
        save(ZoneEntity.builder()
                .leftTop(zone.getLeftTop().toString())
                .leftBottom(zone.getLeftBottom().toString())
                .rightTop(zone.getRightTop().toString())
                .rightBottom(zone.getRightBottom().toString())
                .build());
        return Mono.just(zone);
    }

    public Zone fromStringZoneToModel(ZoneRequestZone zoneDto) {
        return coordsService.makeZone(zoneDto.getLeftTop(),
                zoneDto.getLeftBottom(),
                zoneDto.getRightTop(),
                zoneDto.getRightBottom());
    }

//    public Zone fromStringZoneToModelById(long satelliteId){
//        return fromStringZoneToModel(Objects.requireNonNull(zoneRepository.findById(satelliteId).block()));
//    }
}
