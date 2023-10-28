package com.korotkov.hackathon.service;

import com.korotkov.hackathon.entity.*;
import com.korotkov.hackathon.repository.MapZoneRepository;
import com.korotkov.hackathon.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)


public class OrderService {
    OrderRepository orderRepository;
    UserService userService;
    SatellitesService satellitesService;
    ModelMapper modelMapper;
    MapZoneRepository mapZoneRepository;

    public Flux<OrderEntity> findAllById(long user_id) {
        return orderRepository.findAllByUserId(user_id).publishOn(Schedulers.boundedElastic()).map(order -> {
            OrderEntity orderEntity = modelMapper.map(order, OrderEntity.class);
            Mono<UserEntity> userById = userService.getUserById(order.getUserId());
            Mono<SatelliteEntity> satellite = satellitesService.findByName(order.getSatelliteName());

            Mono<MapZoneEntity> mapZone = mapZoneRepository.findByNorthAndEast(order.getNorth(), order.getEast());

            orderEntity.setMapZone(mapZone.block());
            orderEntity.setSatellite(satellite.block());
            orderEntity.setUserEntity(userById.block());
            return orderEntity;
        });
    }

    @Transactional
    public Mono<Order> save(Order order, long id) {
        order.setUserId(id);
        return orderRepository.save(order);
    }

//    public Long getCurrentUserId() {
//        CustomPrincipal principal = (CustomPrincipal) ReactiveSecurityContextHolder.getContext()
//                .publishOn(Schedulers.boundedElastic())
//                .map(a -> a.getAuthentication().getPrincipal())
//                .block();
//        return principal.getId();
//    }
}
