package com.example.salle_de_sport.infrastructure.database.repositories;

import com.example.salle_de_sport.infrastructure.database.entities.PeriodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodeRepository extends JpaRepository<PeriodeEntity, Long> {
}
