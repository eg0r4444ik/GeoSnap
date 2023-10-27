package com.korotkov.hackathon.util;

import com.korotkov.hackathon.util.coordinatesUtil.CartesianCoordinates;
import com.korotkov.hackathon.util.coordinatesUtil.Point;
import lombok.Data;


@Data
public class Zone {

    private Point leftTop, leftBottom, rightTop, rightBottom;

    public Zone(CartesianCoordinates leftTopCoords,
                CartesianCoordinates leftBottomCoords,
                CartesianCoordinates rightTopCoords,
                CartesianCoordinates rightBottomCoords) {
        this.leftTop = new Point(leftTopCoords);
        this.leftBottom = new Point(leftBottomCoords);
        this.rightTop = new Point(rightTopCoords);
        this.rightBottom = new Point(rightBottomCoords);
    }
}
