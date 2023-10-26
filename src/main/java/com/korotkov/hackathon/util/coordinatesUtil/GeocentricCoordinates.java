package com.korotkov.hackathon.util.coordinatesUtil;

public class GeocentricCoordinates {

    private Latitude latitude;
    private Longitude longitude;

    public GeocentricCoordinates(Latitude latitude, Longitude longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Latitude getLatitude() {
        return latitude;
    }

    public Longitude getLongitude() {
        return longitude;
    }
}
