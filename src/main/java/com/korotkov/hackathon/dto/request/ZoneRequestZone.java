package com.korotkov.hackathon.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ZoneRequestZone {
    String leftTop;
    String leftBottom;
    String rightTop;
    String rightBottom;
}
