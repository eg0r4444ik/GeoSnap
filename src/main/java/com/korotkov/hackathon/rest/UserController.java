package com.korotkov.hackathon.rest;

import com.korotkov.hackathon.dto.request.MapZoneRequestDto;
import com.korotkov.hackathon.dto.request.ZoneRequestZone;
import com.korotkov.hackathon.entity.MapZoneEntity;
import com.korotkov.hackathon.service.CoordsService;
import com.korotkov.hackathon.service.MapZoneService;
import com.korotkov.hackathon.service.ZoneService;
import com.korotkov.hackathon.util.Zone;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    ModelMapper modelMapper;
    MapZoneService mapZoneService;
    ZoneService zoneService;
    CoordsService coordsService;

    @PostMapping("/zone")
    public Mono<Zone> makeZone(ZoneRequestZone zone) {
        return zoneService.save(fromZoneDtoToModel(zone));
    }


    private Zone fromZoneDtoToModel(ZoneRequestZone zoneDto) {
        return coordsService.makeZone(zoneDto.getLeftTop(),
                zoneDto.getLeftBottom(),
                zoneDto.getRightTop(),
                zoneDto.getRightBottom());
    }

    @GetMapping("/mapZone")
    public Flux<MapZoneEntity> findAllZones(){
        return mapZoneService.findAll();
    }

}
