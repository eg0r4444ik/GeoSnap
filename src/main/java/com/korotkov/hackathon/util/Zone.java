package com.korotkov.hackathon.util;

import com.korotkov.hackathon.service.CoordsService;
import org.springframework.beans.factory.annotation.Autowired;

public class Zone {

    @Autowired
    CoordsService service;
    private Point leftTop;
    private Point rightBottom;

    public Zone(String leftTopCoords, String rightBottomCoords) {
        this.leftTop = new Point(service.geocentricToCartesian(service.strToGeocentric(leftTopCoords)));
        this.rightBottom = new Point(service.geocentricToCartesian(service.strToGeocentric(rightBottomCoords)));
    }
}
