package com.project.indytskyi.TripsService.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "track")
public class Track {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private TrafficOrder ownerTrack;

    @Column(name = "latitude")
    private int latitude;

    @Column(name = "longitude")
    private int longitude;

    @Column(name = "speed")
    private int speed;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "distance")
    private double distance;

    public Track() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TrafficOrder getOwnerTrack() {
        return ownerTrack;
    }

    public void setOwnerTrack(TrafficOrder ownerTrack) {
        this.ownerTrack = ownerTrack;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }


    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", owner=" + ownerTrack +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", speed=" + speed +
                ", timestamp=" + timestamp +
                ", distance=" + distance +
                '}';
    }
}
