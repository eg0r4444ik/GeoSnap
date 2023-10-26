package com.korotkov.hackathon.util;

public class Longitude {

    public enum LongitudeType{
        E,
        W
    }

    private LongitudeType type;
    private int degree, minute, second;

    public Longitude(LongitudeType type, int degree, int minute, int second) {
        this.type = type;
        this.degree = degree;
        this.minute = minute;
        this.second = second;
    }
}
