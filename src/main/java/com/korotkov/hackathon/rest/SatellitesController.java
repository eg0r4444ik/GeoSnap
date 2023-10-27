package com.korotkov.hackathon.rest;

import com.korotkov.hackathon.dto.request.SatelliteRequestDto;
import com.korotkov.hackathon.dto.response.SatelliteResponseDto;
import com.korotkov.hackathon.entity.SatelliteEntity;
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
@RequestMapping("/admin/satellites")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SatellitesController {

    SatellitesService satellitesService;
    ModelMapper modelMapper;

    @GetMapping("/all")
    public Flux<SatelliteResponseDto> getAllSatellites() {
        return satellitesService.getSatellites()
                .map(satelliteEntity ->
                        modelMapper.map(satelliteEntity, SatelliteResponseDto.class));
    }

    @PostMapping("/add")
    public Mono<SatelliteResponseDto> addSatellite(@RequestBody @Valid SatelliteRequestDto satelliteRequestDto) {
        Mono<SatelliteEntity> newSatellite = satellitesService.save(modelMapper.map(satelliteRequestDto, SatelliteEntity.class));
        return newSatellite.map(s -> modelMapper.map(s, SatelliteResponseDto.class));
    }

}
