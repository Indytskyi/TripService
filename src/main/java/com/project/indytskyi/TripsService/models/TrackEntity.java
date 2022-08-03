package com.project.indytskyi.TripsService.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Class which interacts with the table Track in which the location of the car will be saved at intervals
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
   // @Pattern(regexp = "\\d+", message = "Latitude should be contains correct data")
    private double latitude;

    @Column(name = "longitude")
  //  @Pattern(regexp = "\\d+", message = "Longitude should be contains correct data")
    private double longitude;

    @Column(name = "speed")
    private int speed;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "distance")
    private double distance;
}
