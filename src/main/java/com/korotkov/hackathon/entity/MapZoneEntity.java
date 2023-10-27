package com.korotkov.hackathon.entity;


import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table("map_zones")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MapZoneEntity {
    @Id
    int id;
    double north;
    double south;
    double east;
    double west;
}
