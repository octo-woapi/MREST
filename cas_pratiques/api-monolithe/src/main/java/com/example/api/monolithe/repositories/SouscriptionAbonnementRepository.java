package com.example.api.monolithe.repositories;

import com.example.api.monolithe.entities.SouscriptionAbonnement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SouscriptionAbonnementRepository extends JpaRepository<SouscriptionAbonnement, Long> {

    @Query(
            "FROM SouscriptionAbonnement WHERE abonnement_id = :id")
    Optional<List<SouscriptionAbonnement>> findAllByAbonnementId(@Param("id") Long id);
}
