package com.korotkov.hackathon.rest;

import com.korotkov.hackathon.dto.request.ZoneRequestZone;
import com.korotkov.hackathon.service.CoordsService;
import com.korotkov.hackathon.service.ZoneService;
import com.korotkov.hackathon.util.Zone;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    ModelMapper modelMapper;
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
}
