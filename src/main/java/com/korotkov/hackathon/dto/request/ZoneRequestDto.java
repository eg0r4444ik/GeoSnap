package com.korotkov.hackathon.dto.request;

import lombok.Data;

@Data
public class ZoneRequestDto {
    Double north;
    Double south;
    Double east;
    Double west;
}
