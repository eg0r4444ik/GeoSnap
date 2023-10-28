package com.korotkov.hackathon.entity;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderEntity {
    long id;
    UserEntity userEntity;
    SatelliteEntity satellite;
    MapZoneEntity mapZone;
    int price;
}
