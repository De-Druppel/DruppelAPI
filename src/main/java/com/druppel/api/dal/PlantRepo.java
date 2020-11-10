package com.druppel.api.dal;

import com.druppel.api.model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantRepo extends JpaRepository<Plant, Integer> {
    Plant findByIdEsp(int idEsp);
}