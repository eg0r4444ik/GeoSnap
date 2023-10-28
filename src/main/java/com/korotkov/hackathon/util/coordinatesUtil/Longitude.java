package com.korotkov.hackathon.util.coordinatesUtil;

public class Longitude {

    private LongitudeType type;
    private double degree, minute, second;

    public Longitude(LongitudeType type, double degree, double minute, double second) {
        this.type = type;
        this.degree = degree;
        this.minute = minute;
        this.second = second;
    }

    public LongitudeType getType() {
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

    public enum LongitudeType {
        E,
        W
    }
}
