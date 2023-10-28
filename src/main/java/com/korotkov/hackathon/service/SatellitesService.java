package com.korotkov.hackathon.service;

import com.korotkov.hackathon.dto.response.SatelliteOrderResponse;
import com.korotkov.hackathon.dto.response.SatelliteResponseDto;
import com.korotkov.hackathon.entity.SatelliteEntity;
import com.korotkov.hackathon.repository.SatellitesRepository;
import com.korotkov.hackathon.util.Zone;
import com.korotkov.hackathon.util.coordinatesUtil.CartesianCoordinates;
import com.korotkov.hackathon.util.coordinatesUtil.Point;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static java.lang.Math.*;


@Service
public class SatellitesService {

    private final SatellitesRepository satellitesRepository;
    private final int EARTH_RADIUS = 6378100;

    private final ModelMapper modelMapper;

    @Autowired
    public SatellitesService(SatellitesRepository satellitesRepository, ModelMapper modelMapper) {
        this.satellitesRepository = satellitesRepository;
        this.modelMapper = modelMapper;
    }


    public Flux<SatelliteEntity> getSatellites() {
        return satellitesRepository.findAll();
    }

    @Transactional
    public Mono<SatelliteEntity> save(SatelliteEntity satelliteEntity) {
        return satellitesRepository.save(satelliteEntity);
    }

    public Mono<SatelliteEntity> findByName(String name) {
        return satellitesRepository.findByName(name);
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

    public List<Point> getSatelliteTrajectory(SatelliteEntity satellite) {
        double R = EARTH_RADIUS + satellite.getDistanceToEarth();
        List<Point> trajectory = new ArrayList<>();
        for (double alpha = 0; alpha <= 2 * PI; alpha += 0.01) {
            Point point = new Point(new CartesianCoordinates(R * cos(satellite.getEarthToOrbitAngle()) * cos(alpha),
                    R * sin(satellite.getEarthToOrbitAngle()) * cos(alpha),
                    R * sin(alpha)));
            trajectory.add(point);
        }

        return trajectory;
    }

    private List<Point> getSatelliteTrajectoryStartedNow(SatelliteEntity satellite) {
        Point start = defineCoords(satellite);
        boolean find = false;

        double R = EARTH_RADIUS + satellite.getDistanceToEarth();
        List<Point> trajectory = new ArrayList<>();
        double border = 2 * PI;
        for (double alpha = 0; alpha <= border; alpha += 0.01) {
            Point point = new Point(new CartesianCoordinates(R * cos(satellite.getEarthToOrbitAngle()) * cos(alpha),
                    R * sin(satellite.getEarthToOrbitAngle()) * cos(alpha),
                    R * sin(alpha)));

            if (Math.abs(start.getCoordinates().getX() - point.getCoordinates().getX()) < 10e-6 &&
                    Math.abs(start.getCoordinates().getY() - point.getCoordinates().getY()) < 10e-6 &&
                    Math.abs(start.getCoordinates().getZ() - point.getCoordinates().getZ()) < 10e-6) {
                find = true;
                border += 2 * PI;
            }

            if (find) {
                trajectory.add(point);
            }
        }

        return trajectory;
    }

    private double getAngleBetweenVectors(Point common, Point normal, Point checkPoint) {
        double x1 = checkPoint.getCoordinates().getX() - common.getCoordinates().getX();
        double y1 = checkPoint.getCoordinates().getY() - common.getCoordinates().getY();
        double z1 = checkPoint.getCoordinates().getZ() - common.getCoordinates().getZ();

        double x2 = normal.getCoordinates().getX() - common.getCoordinates().getX();
        double y2 = normal.getCoordinates().getY() - common.getCoordinates().getY();
        double z2 = normal.getCoordinates().getZ() - common.getCoordinates().getZ();

        double cos = (x1 * x2 + y1 * y2 + z1 * z2) / (Math.sqrt(x1 * x1 + y1 * y1 + z1 * z1) * Math.sqrt(x2 * x2 + y2 * y2 + z2 * z2));
        return acos(cos);
    }
    public boolean doesSatelliteCoverArea(Zone zone, SatelliteEntity satellite) {
        boolean leftTop = false;
        boolean leftBottom = false;
        boolean rightTop = false;
        boolean rightBottom = false;

        double R = EARTH_RADIUS + satellite.getDistanceToEarth();
        for (Point point : getSatelliteTrajectory(satellite)) {
            Point normal = new Point(new CartesianCoordinates(point.getCoordinates().getX() * EARTH_RADIUS / R,
                    point.getCoordinates().getY() * EARTH_RADIUS / R,
                    point.getCoordinates().getZ() * EARTH_RADIUS / R));
            if (Math.abs(getAngleBetweenVectors(point, normal, zone.getLeftTop()) - satellite.getViewAngle()) < 10e-6) {
                leftTop = true;
            }
            if (Math.abs(getAngleBetweenVectors(point, normal, zone.getLeftBottom()) - satellite.getViewAngle()) < 10e-6) {
                leftBottom = true;
            }
            if (Math.abs(getAngleBetweenVectors(point, normal, zone.getRightTop()) - satellite.getViewAngle()) < 10e-6) {
                rightTop = true;
            }
            if (Math.abs(getAngleBetweenVectors(point, normal, zone.getRightBottom()) - satellite.getViewAngle()) < 10e-6) {
                rightBottom = true;
            }
        }
        return leftTop && leftBottom && rightTop && rightBottom;
    }

    public Flux<SatelliteOrderResponse> getSortedSatellites(Zone zone, Flux<SatelliteEntity> satellites) {
        //.filter(d -> doesSatelliteCoverArea(zone,d))
        return satellites
                .map(satelliteEntity -> SatelliteOrderResponse.builder()
                        .satelliteResponseDto(modelMapper.map(satelliteEntity, SatelliteResponseDto.class))
                        .time(getTimeForOrder(zone, satelliteEntity))
                        .price(new Random().nextInt(3, 5) * 1000)
                        .build())
                .sort(Comparator.comparingLong(SatelliteOrderResponse::getTime));
    }

    private Point getLastZonePoint(Zone zone, SatelliteEntity satellite) {
        boolean leftTop = false;
        boolean leftBottom = false;
        boolean rightTop = false;
        boolean rightBottom = false;

        double R = EARTH_RADIUS + satellite.getDistanceToEarth();

        for (Point point : getSatelliteTrajectory(satellite)) {
            Point normal = new Point(new CartesianCoordinates(point.getCoordinates().getX() * EARTH_RADIUS / R,
                    point.getCoordinates().getY() * EARTH_RADIUS / R,
                    point.getCoordinates().getZ() * EARTH_RADIUS / R));

            if (Math.abs(getAngleBetweenVectors(point, normal, zone.getLeftTop()) - satellite.getViewAngle()) < 10e-6) {
                leftTop = true;
                if (leftBottom && rightTop && rightBottom) {
                    return point;
                }
            }
            if (Math.abs(getAngleBetweenVectors(point, normal, zone.getLeftBottom()) - satellite.getViewAngle()) < 10e-6) {
                leftBottom = true;
                if (leftTop && rightTop && rightBottom) {
                    return point;
                }
            }
            if (Math.abs(getAngleBetweenVectors(point, normal, zone.getRightTop()) - satellite.getViewAngle()) < 10e-6) {
                rightTop = true;
                if (leftTop && leftBottom && rightBottom) {
                    return point;
                }
            }
            if (Math.abs(getAngleBetweenVectors(point, normal, zone.getRightBottom()) - satellite.getViewAngle()) < 10e-6) {
                rightBottom = true;
                if (leftTop && leftBottom && rightTop) {
                    return point;
                }
            }
        }

        return null;
    }

    public long getTimeForOrder(Zone zone, SatelliteEntity satellite) {
        Point lastPoint = getLastZonePoint(zone, satellite);
//        double alpha = getAngleBetweenVectors(new Point(new CartesianCoordinates(0, 0, 0)),
//                lastPoint, defineCoords(satellite));
//        long result = (long)Math.ceil(satellite.getOrbitPeriod()*alpha/(2*PI));
        return new Random().nextLong(1_706_415_739, 1_738_038_139);
    }

}
