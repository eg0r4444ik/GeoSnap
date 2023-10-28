package com.korotkov.hackathon.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SatelliteOrderResponse {
    SatelliteResponseDto satelliteResponseDto;
    long time;
    int price;
}
