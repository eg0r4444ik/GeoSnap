package com.korotkov.hackathon.repository;

import com.korotkov.hackathon.entity.ZoneEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ZoneRepository extends R2dbcRepository<ZoneEntity, Long> {

}

