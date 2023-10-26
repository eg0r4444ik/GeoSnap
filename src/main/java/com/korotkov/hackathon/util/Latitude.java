package com.korotkov.hackathon.util;

public class Latitude {

    public enum LatitudeType{
        N,
        S
    }

    private LatitudeType type;
    private int degree, minute, second;

    public Latitude(LatitudeType type, int degree, int minute, int second) {
        this.type = type;
        this.degree = degree;
        this.minute = minute;
        this.second = second;
    }
}
