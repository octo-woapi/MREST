package com.example.salle_de_sport.infrastructure.database.repositories;

import com.example.salle_de_sport.infrastructure.database.entities.SouscriptionAbonnementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SouscriptionAbonnementRepository extends JpaRepository<SouscriptionAbonnementEntity, Long> {

    @Query(
            "FROM SouscriptionAbonnementEntity WHERE abonnement_id = :id")
    List<SouscriptionAbonnementEntity> findAllByAbonnementId(@Param("id") Long id);
}
