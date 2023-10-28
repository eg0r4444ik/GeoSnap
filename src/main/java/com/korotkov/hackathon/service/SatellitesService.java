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
        if (Math.abs(x-y) < 10e-6 && abs(x * x + z * z - 1) < 10e-6) {
            return true;
        }
        return false;
    }

    public List<Point> getSatelliteTrajectory(SatelliteEntity satellite){
        double R = EARTH_RADIUS + satellite.getDistanceToEarth();
        List<Point> trajectory = new ArrayList<>();
        for(double alpha = 0; alpha <= 2*PI; alpha += 0.01){
            Point point = new Point(new CartesianCoordinates(R * cos(satellite.getEarthToOrbitAngle()) * cos(alpha),
                    R * sin(satellite.getEarthToOrbitAngle()) * cos(alpha),
                    R * sin(alpha)));
            trajectory.add(point);
        }

        return trajectory;
    }

    private List<Point> getSatelliteTrajectoryStartedNow(SatelliteEntity satellite){
        Point start = defineCoords(satellite);
        boolean find = false;

        double R = EARTH_RADIUS + satellite.getDistanceToEarth();
        List<Point> trajectory = new ArrayList<>();
        double border = 2*PI;
        for(double alpha = 0; alpha <= border; alpha += 0.01){
            Point point = new Point(new CartesianCoordinates(R * cos(satellite.getEarthToOrbitAngle()) * cos(alpha),
                    R * sin(satellite.getEarthToOrbitAngle()) * cos(alpha),
                    R * sin(alpha)));

            if(Math.abs(start.getCoordinates().getX()-point.getCoordinates().getX()) < 10e-6 &&
                    Math.abs(start.getCoordinates().getY()-point.getCoordinates().getY()) < 10e-6 &&
                    Math.abs(start.getCoordinates().getZ()-point.getCoordinates().getZ()) < 10e-6){
                find = true;
                border += 2*PI;
            }

            if(find){
                trajectory.add(point);
            }
        }

        return trajectory;
    }

    private double getAngleBetweenVectors(Point common, Point normal, Point checkPoint){
        double x1 = checkPoint.getCoordinates().getX() - common.getCoordinates().getX();
        double y1 = checkPoint.getCoordinates().getY() - common.getCoordinates().getY();
        double z1 = checkPoint.getCoordinates().getZ() - common.getCoordinates().getZ();

        double x2 = normal.getCoordinates().getX() - common.getCoordinates().getX();
        double y2 = normal.getCoordinates().getY() - common.getCoordinates().getY();
        double z2 = normal.getCoordinates().getZ() - common.getCoordinates().getZ();

        double cos = (x1*x2+y1*y2+z1*z2)/(Math.sqrt(x1*x1+y1*y1+z1*z1)*Math.sqrt(x2*x2+y2*y2+z2*z2));
        return acos(cos);
    }

    // TODO оптимизировать метод
    public boolean doesSatelliteCoverArea(Zone zone, SatelliteEntity satellite){
        boolean leftTop = false;
        boolean leftBottom = false;
        boolean rightTop = false;
        boolean rightBottom = false;

        double R = EARTH_RADIUS+satellite.getDistanceToEarth();
        for(Point point : getSatelliteTrajectory(satellite)){
            Point normal = new Point(new CartesianCoordinates(point.getCoordinates().getX()*EARTH_RADIUS/R,
                    point.getCoordinates().getY()*EARTH_RADIUS/R,
                    point.getCoordinates().getZ()*EARTH_RADIUS/R));
            if(getAngleBetweenVectors(point, normal, zone.getLeftTop()) <= satellite.getViewAngle()/2){
                leftTop = true;
            }
            if(getAngleBetweenVectors(point, normal, zone.getLeftBottom()) <= satellite.getViewAngle()/2){
                leftBottom = true;
            }
            if(getAngleBetweenVectors(point, normal, zone.getRightTop()) <= satellite.getViewAngle()/2){
                rightTop = true;
            }
            if(getAngleBetweenVectors(point, normal, zone.getRightBottom()) <= satellite.getViewAngle()/2){
                rightBottom = true;
            }
        }
        return leftTop && leftBottom && rightTop && rightBottom;
    }

    public int getPrice(Zone zone){
        double x1 = zone.getLeftTop().getCoordinates().getX();
        double y1 = zone.getLeftTop().getCoordinates().getY();
        double z1 = zone.getLeftTop().getCoordinates().getZ();

        double x2 = zone.getRightTop().getCoordinates().getX();
        double y2 = zone.getRightTop().getCoordinates().getY();
        double z2 = zone.getRightTop().getCoordinates().getZ();

        double x3 = zone.getRightBottom().getCoordinates().getX();
        double y3 = zone.getRightBottom().getCoordinates().getY();
        double z3 = zone.getRightBottom().getCoordinates().getZ();

        double x4 = zone.getLeftBottom().getCoordinates().getX();
        double y4 = zone.getLeftBottom().getCoordinates().getY();
        double z4 = zone.getLeftBottom().getCoordinates().getZ();

        double a = Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1) + (z2-z1)*(z2-z1));
        double b = Math.sqrt((x4-x1)*(x4-x1) + (y4-y1)*(y4-y1) + (z4-z1)*(z4-z1));
        double s = a*b/10000000000L;

        return (int)s;
    }

    public Flux<SatelliteOrderResponse> getSortedSatellites(Zone zone, Flux<SatelliteEntity> satellites) {
        //.filter(d -> doesSatelliteCoverArea(zone,d))
        return satellites
                .map(satelliteEntity -> SatelliteOrderResponse.builder()
                        .satelliteResponseDto(modelMapper.map(satelliteEntity, SatelliteResponseDto.class))
                        .time(getTimeForOrder(zone, satelliteEntity))
                        .price(getPrice(zone))
                        .build())
                .sort(Comparator.comparingLong(SatelliteOrderResponse::getTime));
    }

    public Point getLastZonePoint(Zone zone, SatelliteEntity satellite){
        boolean leftTop = false;
        boolean leftBottom = false;
        boolean rightTop = false;
        boolean rightBottom = false;

        double R = EARTH_RADIUS+satellite.getDistanceToEarth();

        for(Point point : getSatelliteTrajectory(satellite)){
            Point normal = new Point(new CartesianCoordinates(point.getCoordinates().getX()*EARTH_RADIUS/R,
                    point.getCoordinates().getY()*EARTH_RADIUS/R,
                    point.getCoordinates().getZ()*EARTH_RADIUS/R));

            if(getAngleBetweenVectors(point, normal, zone.getLeftTop()) <= satellite.getViewAngle()/2){
                leftTop = true;
                if(leftBottom && rightTop && rightBottom){
                    return point;
                }
            }
            if(getAngleBetweenVectors(point, normal, zone.getLeftBottom()) <= satellite.getViewAngle()/2){
                leftBottom = true;
                if(leftTop && rightTop && rightBottom){
                    return point;
                }
            }
            if(getAngleBetweenVectors(point, normal, zone.getRightTop()) <= satellite.getViewAngle()/2){
                rightTop = true;
                if(leftTop && leftBottom && rightBottom){
                    return point;
                }
            }
            if(getAngleBetweenVectors(point, normal, zone.getRightBottom()) <= satellite.getViewAngle()/2){
                rightBottom = true;
                if(leftTop && leftBottom && rightTop){
                    return point;
                }
            }
        }

        return null;
    }

    public long getTimeForOrder(Zone zone, SatelliteEntity satellite){
        Point lastPoint = getLastZonePoint(zone, satellite);
        double alpha = getAngleBetweenVectors(new Point(new CartesianCoordinates(0, 0, 0)),
                lastPoint, defineCoords(satellite));
        long result = (long)Math.ceil(satellite.getOrbitPeriod()*alpha/(2*PI));
        return result;
    }

    public boolean canSatellitesCrush(SatelliteEntity satellite){
        Flux<SatelliteEntity> satellites = getSatellites();
        for(SatelliteEntity sat : satellites.toIterable()){
            if(sat.getDistanceToEarth() == satellite.getDistanceToEarth() &&
            sat.getOrbitPeriod() != satellite.getOrbitPeriod()){
                return true;
            }
        }

        return false;
    }

    public boolean possibilityAddedSatellite(SatelliteEntity satellite){
        if(!canSatellitesCrush(satellite) && satellite.getDistanceToEarth() >= 200000){
            return true;
        }
        return false;
    }

    public List<String> getPossibleZonesForSatellite(SatelliteEntity satellite){
        List<String> result = new ArrayList<>();
        double l = satellite.getDistanceToEarth()*tan(satellite.getViewAngle()/2);
        double R = EARTH_RADIUS+satellite.getDistanceToEarth();
        double beta = atan(l/(2*R));
        for(double alpha = 0; alpha <= 2*PI; alpha += 0.01){
            Point point = new Point(new CartesianCoordinates(R * cos(satellite.getEarthToOrbitAngle()) * cos(alpha),
                    R * sin(satellite.getEarthToOrbitAngle()) * cos(alpha),
                    R * sin(alpha)));
            Point normal = new Point(new CartesianCoordinates(point.getCoordinates().getX()*EARTH_RADIUS/R,
                    point.getCoordinates().getY()*EARTH_RADIUS/R,
                    point.getCoordinates().getZ()*EARTH_RADIUS/R));

            String longitude = "";
            if(alpha <= PI) {
                longitude = alpha + " ";
            }else{
                longitude = (alpha-2*PI) + " ";
            }

            double x = normal.getCoordinates().getX();
            double y = normal.getCoordinates().getY();
            double z = normal.getCoordinates().getZ();
            double a = acos((x*x+z*z)/(sqrt(x*x+y*y+z*z)*sqrt(x*x+z*z)));
            String latitude1 = "";
            String latitude2 = "";
            if(a+beta <= PI){
                latitude1 = (a+beta)+ " ";
            }else{
                latitude1 = (a+beta-2*PI) + " ";
            }
            if(a-beta <= PI){
                latitude2 = (a-beta)+ " ";
            }else if(a-beta <= -PI){
                latitude2 = (a-beta+2*PI) + " ";
            }else{
                latitude2 = (a-beta-2*PI) + " ";
            }

            result.add(latitude1 + latitude2 + longitude);
        }
        return result;
    }

    public List<List<String>> getPossibleZones(){
        List<List<String>> result = new ArrayList<>();
        for(SatelliteEntity satellite : getSatellites().toIterable()){
            result.add(getPossibleZonesForSatellite(satellite));
        }
        return result;
    }
}
