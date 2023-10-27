package com.korotkov.hackathon.util.coordinatesUtil;

public class Latitude {

    private LatitudeType type;
    private double degree, minute, second;
    public Latitude(LatitudeType type, double degree, double minute, double second) {
        this.type = type;
        this.degree = degree;
        this.minute = minute;
        this.second = second;
    }

    public LatitudeType getType() {
        return type;
    }

    public double getDegree() {
        return degree;
    }

    public double getMinute() {
        return minute;
    }

    public double getSecond() {
        return second;
    }

    public enum LatitudeType {
        N,
        S
    }
}
