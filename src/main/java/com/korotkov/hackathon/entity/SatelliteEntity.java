package com.korotkov.hackathon.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table("satellites")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SatelliteEntity {
    @Id
    private Long id;

    @Column(value = "orbit_period")

    double orbitPeriod;

    @Column(value = "earth_to_orbit_angle")

    double earthToOrbitAngle;

    @Column(value = "distance_to_earth")

    double distanceToEarth;

    @Column(value = "view_angle")

    double viewAngle;

    @Column(value = "time_start")

    double timeStart;


}
