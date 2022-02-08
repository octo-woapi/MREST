package com.example.salle_de_sport.infrastructure.database.repositories;

import com.example.salle_de_sport.infrastructure.database.entities.AbonnementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbonnementRepository extends JpaRepository<AbonnementEntity, Long> {}
