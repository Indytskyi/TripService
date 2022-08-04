package com.project.indytskyi.tripsservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 * Class which interacts with the table Track in which the location of the car
 * will be saved at intervals
 */
@Entity
@Data
@Table(name = "track")
public class TrackEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private TrafficOrderEntity ownerTrack;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "speed")
    private int speed;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "distance")
    private double distance;
}
