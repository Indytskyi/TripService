package com.project.indytskyi.TripsService.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "traffic_order")
public class TrafficOrder {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "the column car_id must be not empty")
    @Column(name = "car_id")
    private long carId;

    @NotEmpty(message = "the column user_id must be not empty")
    @Column(name = "user_id")
    private long userId;

    @Column(name = "booking_time")
    private LocalDateTime bookingTime;

    @Column(name = "activation_time")
    private LocalDateTime activationTime;

    @Column(name = "completion_time")
    private LocalDateTime completionTime;

    @Column(name = "status")
    private String status;

    @Column(name = "status_paid")
    private String statusPaid;


    @OneToMany(mappedBy = "ownerTrack")
    List<Track> tracks;

    @OneToMany(mappedBy = "ownerImage")
    List<Images> images;


    public TrafficOrder() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public LocalDateTime getActivationTime() {
        return activationTime;
    }

    public void setActivationTime(LocalDateTime activationTime) {
        this.activationTime = activationTime;
    }

    public LocalDateTime getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(LocalDateTime completionTime) {
        this.completionTime = completionTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusPaid() {
        return statusPaid;
    }

    public void setStatusPaid(String statusPaid) {
        this.statusPaid = statusPaid;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "TrafficOrder{" +
                "id=" + id +
                ", carId=" + carId +
                ", userId=" + userId +
                ", bookingTime=" + bookingTime +
                ", activationTime=" + activationTime +
                ", completionTime=" + completionTime +
                ", status='" + status + '\'' +
                ", statusPaid='" + statusPaid + '\'' +
                '}';
    }
}
