package com.korotkov.hackathon.rest;

import com.korotkov.hackathon.dto.request.MapZoneRequestDto;
import com.korotkov.hackathon.dto.request.OrderRequestDto;
import com.korotkov.hackathon.dto.request.ZoneRequestDto;
import com.korotkov.hackathon.dto.response.MapZoneResponseDto;
import com.korotkov.hackathon.dto.response.OrderResponseDto;
import com.korotkov.hackathon.dto.response.SatelliteOrderResponse;
import com.korotkov.hackathon.entity.MapZoneEntity;
import com.korotkov.hackathon.entity.Order;
import com.korotkov.hackathon.security.CustomPrincipal;
import com.korotkov.hackathon.service.CoordsService;
import com.korotkov.hackathon.service.MapZoneService;
import com.korotkov.hackathon.service.OrderService;
import com.korotkov.hackathon.service.SatellitesService;
import com.korotkov.hackathon.util.Zone;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
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
    CoordsService coordsService;
    OrderService orderService;
    SatellitesService satellitesService;


    @PostMapping("/find")
    public Flux<SatelliteOrderResponse> findAllPath(@RequestBody ZoneRequestDto zoneRequest) {
        Zone zone = fromZoneDtoToModel(zoneRequest);
//        zoneService.save(modelMapper.map(zoneRequest, ZoneEntity.class));
//        zoneServic

        return satellitesService.getSortedSatellites(zone, satellitesService.getSatellites());
    }


//    @GetMapping("/mapZone")
//    public Flux<MapZoneEntity> findAllZones(){
//        return mapZoneService.findAll();
//    }

    @GetMapping("/order/all")
    public Flux<OrderResponseDto> findAll(Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        return orderService.findAllById(principal.getId()).map(orderEntity -> modelMapper.map(orderEntity, OrderResponseDto.class));
    }

    @PostMapping("/zone")
    public Mono<MapZoneEntity> save(@RequestBody MapZoneRequestDto mapZoneRequestDto) {
        return mapZoneService.save(modelMapper.map(mapZoneRequestDto, MapZoneEntity.class));
    }

    @GetMapping("/map-zone/all")
    public Flux<MapZoneResponseDto> getAllBanZones() {
        return mapZoneService.findAllBanZones().map(z -> modelMapper.map(z, MapZoneResponseDto.class));
    }


    @PostMapping("/order")
    public Mono<OrderResponseDto> save(@RequestBody OrderRequestDto orderRequest, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();

        mapZoneService.save(modelMapper.map(orderRequest.getZoneRequestDto(), MapZoneEntity.class));

        Order map = modelMapper.map(orderRequest, Order.class);
        map.setNorth(orderRequest.getZoneRequestDto().getNorth());
        map.setEast(orderRequest.getZoneRequestDto().getEast());
        Mono<Order> save = orderService.save(map, principal.getId());
        return save.map(order -> modelMapper.map(order, OrderResponseDto.class));
    }

    private Zone fromZoneDtoToModel(ZoneRequestDto zoneDto) {
        return coordsService.makeZone(zoneDto.getWest() + " " + zoneDto.getNorth(),
                zoneDto.getWest() + " " + zoneDto.getSouth(),
                zoneDto.getEast() + " " + zoneDto.getNorth(),
                zoneDto.getEast() + " " + zoneDto.getSouth());
    }


}
