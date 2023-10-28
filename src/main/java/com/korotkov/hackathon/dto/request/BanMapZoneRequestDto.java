package com.korotkov.hackathon.dto.request;


import lombok.Data;

@Data
public class BanMapZoneRequestDto {
    MapZoneRequestDto mapZoneRequestDto;
    boolean isAllowed;
}
