package com.project.indytskyi.TripsService.repositories;

import com.project.indytskyi.TripsService.models.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalancesRepository extends JpaRepository<Balance, Long> {

}
