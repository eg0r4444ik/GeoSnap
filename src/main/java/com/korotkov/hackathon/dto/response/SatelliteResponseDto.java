package com.korotkov.hackathon.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.korotkov.hackathon.util.coordinatesUtil.CartesianCoordinates;
import lombok.Data;

@Data
public class SatelliteResponseDto {

    double orbitPeriod;

    double earthToOrbitAngle;

    double distanceToEarth;

    double viewAngle;

    double timeStart;

    CartesianCoordinates coordinates;
}