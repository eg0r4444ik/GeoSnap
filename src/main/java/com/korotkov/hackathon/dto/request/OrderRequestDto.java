package com.korotkov.hackathon.dto.request;


import lombok.Data;

@Data
public class OrderRequestDto {
    ZoneRequestDto zoneRequestDto;
    String satelliteName;
    int price;

}
