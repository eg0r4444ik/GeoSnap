package com.korotkov.hackathon.dto.response;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
public class MapZoneResponseDto {
    double north;
    double south;
    double east;
    double west;
}
