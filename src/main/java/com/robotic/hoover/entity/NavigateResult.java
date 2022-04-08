package com.robotic.hoover.entity;

import com.robotic.hoover.dto.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "navigate_result")
public class NavigateResult extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "navigate_request_id")
    private NavigateRequest navigateRequest;

    @Type(type = "json")
    @Column(columnDefinition = "json", nullable = false)
    private Coordinate finalPosition;

    @Column()
    private int noOfPatchesCovered;

}
