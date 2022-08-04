package com.project.indytskyi.tripsservice.repositories;

import com.project.indytskyi.tripsservice.models.TrackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TracksRepository extends JpaRepository<TrackEntity, Long> {

}
