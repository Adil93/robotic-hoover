package com.robotic.hoover.entity;

import com.robotic.hoover.dto.Coordinate;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

@Data
@Entity
@Table(name = "navigate_request")
public class NavigateRequest extends BaseEntity {
    @Type(type = "json")
    @Column(columnDefinition = "json", nullable = false)
    private Coordinate maxRoomCoordinate;

    @Type(type = "json")
    @Column(columnDefinition = "json", nullable = false)
    private Coordinate currentCoordinate;

    @Column()
    private String instructions;

    @Type(type = "json")
    @Column(columnDefinition = "json", nullable = false)
    private Set<Coordinate> patches;
}
