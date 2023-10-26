package com.korotkov.hackathon.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SatelliteRequestDto {

    double orbitPeriod;

    double earthToOrbitAngle;

    double distanceToEarth;

    double viewAngle;

    double timeStart;
}
