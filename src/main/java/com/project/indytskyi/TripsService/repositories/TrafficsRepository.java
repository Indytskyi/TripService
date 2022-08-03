package com.project.indytskyi.TripsService.repositories;

import com.project.indytskyi.TripsService.models.TrafficOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TrafficsRepository extends JpaRepository<TrafficOrderEntity, Long>{

}
