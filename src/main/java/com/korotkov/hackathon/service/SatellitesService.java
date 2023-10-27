package com.korotkov.hackathon.service;

import com.korotkov.hackathon.entity.SatelliteEntity;
import com.korotkov.hackathon.repository.SatellitesRepository;
import com.korotkov.hackathon.util.coordinatesUtil.CartesianCoordinates;
import com.korotkov.hackathon.util.coordinatesUtil.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.lang.Math.*;


@Service
public class SatellitesService {

    private final SatellitesRepository satellitesRepository;
    private final int EARTH_RADIUS = 6378100;

    @Autowired
    public SatellitesService(SatellitesRepository satellitesRepository) {
        this.satellitesRepository = satellitesRepository;
    }


    public Flux<SatelliteEntity> getSatellites() {
        return satellitesRepository.findAll();
    }

    @Transactional
    public Mono<SatelliteEntity> save(SatelliteEntity satelliteEntity) {
        return satellitesRepository.save(satelliteEntity);
    }

    // Возвращает точку, в которой в данный момент находится спутник
    public Point defineCoords(SatelliteEntity satellite) {
        long currentTime = System.currentTimeMillis();
        long time = (currentTime - satellite.getTimeStart()) % satellite.getOrbitPeriod();
        double alpha = 2 * PI * time * 1.0 / satellite.getOrbitPeriod();
        double R = EARTH_RADIUS + satellite.getDistanceToEarth();
        double x = R * cos(satellite.getEarthToOrbitAngle()) * cos(alpha);
        double y = R * sin(satellite.getEarthToOrbitAngle()) * cos(alpha);
        double z = R * sin(alpha);
        return new Point(new CartesianCoordinates(x, y, z));
    }

    // Определяет принадлежит ли точка траектории спутника
    public boolean belongingPointToTrajectory(Point point, SatelliteEntity satellite) {
        double R = EARTH_RADIUS + satellite.getDistanceToEarth();
        double x = point.getCoordinates().getX() / (R * cos(satellite.getEarthToOrbitAngle()));
        double y = point.getCoordinates().getY() / (R * sin(satellite.getEarthToOrbitAngle()));
        double z = point.getCoordinates().getZ() / R;
        if (x == y && abs(x * x + z * z - 1) < 10e-6) {
            return true;
        }
        return false;
    }


    // TODO Учесть вращение земли вокруг своей оси
}
