package com.project.indytskyi.TripsService.repositories;

import com.project.indytskyi.TripsService.models.TrafficOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrafficsRepository extends JpaRepository<TrafficOrder, Integer>{
}
