package com.korotkov.hackathon.rest;

import com.korotkov.hackathon.dto.request.BanMapZoneRequestDto;
import com.korotkov.hackathon.dto.request.SatelliteRequestDto;
import com.korotkov.hackathon.dto.response.MapZoneResponseDto;
import com.korotkov.hackathon.dto.response.SatelliteResponseDto;
import com.korotkov.hackathon.entity.MapZoneEntity;
import com.korotkov.hackathon.entity.SatelliteEntity;
import com.korotkov.hackathon.service.MapZoneService;
import com.korotkov.hackathon.service.SatellitesService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/admin")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminController {

    SatellitesService satellitesService;
    MapZoneService mapZoneService;
    ModelMapper modelMapper;

    @GetMapping("/satellite/all")
    public Flux<SatelliteResponseDto> getAllSatellites() {
        return satellitesService.getSatellites()
                .map(satelliteEntity -> {
                    SatelliteResponseDto map = modelMapper.map(satelliteEntity, SatelliteResponseDto.class);
                    map.setCoordinates(satellitesService.defineCoords(satelliteEntity).getCoordinates());
                    return map;
                });
    }

    @PostMapping("/satellite")
    public Mono<SatelliteResponseDto> addSatellite(@RequestBody @Valid SatelliteRequestDto satelliteRequestDto) {
        Mono<SatelliteEntity> newSatellite = satellitesService.save(fromSatelliteDtoRequest(satelliteRequestDto));
        return newSatellite.map(s -> modelMapper.map(s, SatelliteResponseDto.class));
    }


    @PostMapping("/map-zone")
    public Mono<MapZoneEntity> banZone(@RequestBody BanMapZoneRequestDto banMapZoneRequestDto) {
        MapZoneEntity map = modelMapper.map(banMapZoneRequestDto, MapZoneEntity.class);

        return mapZoneService.save(map, banMapZoneRequestDto.isAllowed());
    }


    @GetMapping("/map-zone/all")
    public Flux<MapZoneResponseDto> findAllMapZones() {
        return mapZoneService.findAll().map(mapZone -> modelMapper.map(mapZone, MapZoneResponseDto.class));
    }


    private SatelliteEntity fromSatelliteDtoRequest(SatelliteRequestDto s) {
        SatelliteEntity map = modelMapper.map(s, SatelliteEntity.class);
        map.setTimeStart(System.currentTimeMillis());
        long os = ((((s.getDay() * 24L + s.getHours()) * 60 + s.getMinute()) * 60L) + s.getSecond()) * 1000 + s.getMilliseconds();
        long oe = 24 * 60 * 60 * 1000;
//        map.setOrbitPeriod();
        map.setOrbitPeriod(Math.abs(os / (oe - os) * oe));
        return map;
    }
}
