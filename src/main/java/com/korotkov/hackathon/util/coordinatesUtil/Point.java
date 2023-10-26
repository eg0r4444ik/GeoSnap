package com.korotkov.hackathon.util.coordinatesUtil;

import com.korotkov.hackathon.util.coordinatesUtil.CartesianCoordinates;

public class Point {

    private CartesianCoordinates coordinates;

    public Point(CartesianCoordinates coordinates) {
        this.coordinates = coordinates;
    }

    public CartesianCoordinates getCoordinates() {
        return coordinates;
    }
}
