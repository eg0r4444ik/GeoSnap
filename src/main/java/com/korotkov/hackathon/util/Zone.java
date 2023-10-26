package com.korotkov.hackathon.util;

import com.korotkov.hackathon.service.CoordsService;
import com.korotkov.hackathon.util.coordinatesUtil.Point;
import org.springframework.beans.factory.annotation.Autowired;

public class Zone {

    @Autowired
    CoordsService service;
    private Point leftTop, leftBottom, rightTop, rightBottom;

    public Zone(String leftTopCoords, String leftBottomCoords, String rightTopCoords, String rightBottomCoords) {
        this.leftTop = new Point(service.geocentricToCartesian(leftTopCoords));
        this.leftBottom = new Point(service.geocentricToCartesian(leftBottomCoords));
        this.rightTop = new Point(service.geocentricToCartesian(rightTopCoords));
        this.rightBottom = new Point(service.geocentricToCartesian(rightBottomCoords));
    }
}
