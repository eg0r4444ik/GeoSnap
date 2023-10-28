package com.korotkov.hackathon.dto.request;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MapZoneRequestDto {
    double north;
    double south;
    double east;
    double west;
}
