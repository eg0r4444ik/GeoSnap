package com.korotkov.hackathon.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table("orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

    @Id
    long id;

    @Column(value = "user_id")
    long userId;

    int price;

    @Column(value = "satellite_name")
    String satelliteName;

    double north;

    double east;
}
