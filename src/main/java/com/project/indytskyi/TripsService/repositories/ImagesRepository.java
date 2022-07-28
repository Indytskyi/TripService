package com.project.indytskyi.TripsService.repositories;


import com.project.indytskyi.TripsService.models.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagesRepository extends JpaRepository<Images, Long> {

}
