package com.korotkov.hackathon.util.coordinatesUtil;

public class CartesianCoordinates {

    private final double x;
    private double y;
    private double z;

    public CartesianCoordinates(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    @Override
    public String toString() {
        return x + " " + y + " " + z;
    }
}
