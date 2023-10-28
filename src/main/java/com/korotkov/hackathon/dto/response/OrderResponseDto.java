package com.korotkov.hackathon.dto.response;


import com.korotkov.hackathon.entity.SatelliteEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    long id;
    int price;
    SatelliteEntity satellite;
    MapZoneResponseDto mapZone;
}
