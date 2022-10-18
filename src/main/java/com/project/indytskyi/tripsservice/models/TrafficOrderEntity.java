package com.project.indytskyi.tripsservice.models;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *  Class which interacts with the table Traffic order and contain data od travel
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true, builderMethodName = "of")
@Table(name = "traffic_order")
@ToString
public class TrafficOrderEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "car_id")
    private long carId;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "tariff_id")
    private long tariffId;

    @Column(name = "activation_time")
    private LocalDateTime activationTime;

    @Column(name = "completion_time")
    private LocalDateTime completionTime;

    @Column(name = "status")
    private String status;

    @Column(name = "tariff")
    private double tariff;

    @Column(name = "currency")
    private String currency;

    @OneToMany(mappedBy = "ownerTrack")
    private List<TrackEntity> tracks;

    @OneToMany(mappedBy = "ownerImage")
    private List<ImagesEntity> images;


}
