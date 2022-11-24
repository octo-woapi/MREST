package com.example.formule_service.infrastructure.database.repositories;

import com.example.formule_service.infrastructure.database.entities.FormuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormuleRepository extends JpaRepository<FormuleEntity, Long> {
}
