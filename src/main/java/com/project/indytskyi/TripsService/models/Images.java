package com.project.indytskyi.TripsService.models;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class Images {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private TrafficOrder ownerImage;

    @Column(name = "image")
    private String image;

    public Images() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TrafficOrder getOwnerImage() {
        return ownerImage;
    }

    public void setOwnerImage(TrafficOrder ownerImage) {
        this.ownerImage = ownerImage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Images{" +
                "id=" + id +
                ", owner=" + ownerImage +
                ", image='" + image + '\'' +
                '}';
    }
}
