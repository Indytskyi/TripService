package com.project.indytskyi.tripsservice.repositories;

import com.project.indytskyi.tripsservice.models.TrafficOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrafficsRepository extends JpaRepository<TrafficOrderEntity, Long> {

}
