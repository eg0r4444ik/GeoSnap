package com.korotkov.hackathon.repository;

import com.korotkov.hackathon.entity.Order;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;


@Repository
public interface OrderRepository extends R2dbcRepository<Order, Integer> {
    Flux<Order> findAllByUserId(long userId);
}
