package com.example.salle_de_sport.infrastructure.database.repositories;

import com.example.salle_de_sport.infrastructure.database.entities.FormuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormuleRepository extends JpaRepository<FormuleEntity, Long> {
}
