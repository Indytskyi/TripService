package com.project.indytskyi.TripsService.models;

import javax.persistence.*;

@Entity
@Table(name = "history_transaction")
public class HistoryTransaction {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "replenishment_amount")
    private int replenishmentAmount;

    @Column(name = "user_id")
    private int userId;

    public HistoryTransaction() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getReplenishmentAmount() {
        return replenishmentAmount;
    }

    public void setReplenishmentAmount(int replenishmentAmount) {
        this.replenishmentAmount = replenishmentAmount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "HistoryTransaction{" +
                "id=" + id +
                ", replenishmentAmount=" + replenishmentAmount +
                ", userId=" + userId +
                '}';
    }
}
