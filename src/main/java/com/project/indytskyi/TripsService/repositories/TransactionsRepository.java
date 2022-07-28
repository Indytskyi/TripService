package com.project.indytskyi.TripsService.repositories;

import com.project.indytskyi.TripsService.models.HistoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<HistoryTransaction, Long> {
}
