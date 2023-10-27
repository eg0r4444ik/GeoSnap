package com.korotkov.hackathon.util.coordinatesUtil;

public class Point {

    private CartesianCoordinates coordinates;

    public Point(CartesianCoordinates coordinates) {
        this.coordinates = coordinates;
    }

    public CartesianCoordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public String toString() {
        return coordinates.toString();
    }
}
