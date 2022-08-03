package com.project.indytskyi.TripsService.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 *  Class which interacts with the table Traffic order and contain data od travel
 */
@Entity
@Data
@Table(name = "traffic_order")
public class TrafficOrderEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "car_id")
    private long carId;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "activation_time")
    private LocalDateTime activationTime;

    @Column(name = "completion_time")
    private LocalDateTime completionTime;

    @Column(name = "status")
    private String status;

    @Column(name = "status_paid")
    private String statusPaid;

    @Column(name = "balance")
    private Double balance;

    @JsonIgnore
    @OneToMany(mappedBy = "ownerTrack")
    private List<TrackEntity> tracks;

    @JsonIgnore
    @OneToMany(mappedBy = "ownerImage")
    private List<ImagesEntity> images;

}
