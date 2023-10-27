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
@Table("zones")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ZoneEntity {
    @Id
    int id;

    @Column("left_top")
    String leftTop;

    @Column("left_bottom")
    String leftBottom;

    @Column("right_top")
    String rightTop;

    @Column("right_bottom")
    String rightBottom;

}
