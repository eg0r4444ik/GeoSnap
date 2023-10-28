package com.korotkov.hackathon.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SatelliteRequestDto {

    double earthToOrbitAngle;

    double distanceToEarth;

    double viewAngle;

    String name;

    int day;
    int hours;
    int minute;
    int second;
    int milliseconds;
}
