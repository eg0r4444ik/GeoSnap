package com.korotkov.hackathon.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SatelliteResponseDto {

    double orbitPeriod;

    double earthToOrbitAngle;

    double distanceToEarth;

    double viewAngle;

    double timeStart;
}