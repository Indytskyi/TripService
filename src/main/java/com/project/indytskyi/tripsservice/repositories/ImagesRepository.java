package com.project.indytskyi.tripsservice.repositories;

import com.project.indytskyi.tripsservice.models.ImagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagesRepository extends JpaRepository<ImagesEntity, Long> {

}
