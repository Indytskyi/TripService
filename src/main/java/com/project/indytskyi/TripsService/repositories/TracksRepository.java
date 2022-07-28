package com.project.indytskyi.TripsService.repositories;

import com.project.indytskyi.TripsService.models.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TracksRepository extends JpaRepository<Track, Long> {
}
