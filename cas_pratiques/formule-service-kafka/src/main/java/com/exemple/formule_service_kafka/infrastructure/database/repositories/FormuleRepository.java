package com.exemple.formule_service_kafka.infrastructure.database.repositories;

import com.exemple.formule_service_kafka.infrastructure.database.entities.FormuleEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormuleRepository extends JpaRepository<FormuleEntity, UUID> {}
