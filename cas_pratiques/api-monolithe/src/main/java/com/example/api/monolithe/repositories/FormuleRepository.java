package com.example.api.monolithe.repositories;

import com.example.api.monolithe.entities.Formule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormuleRepository extends JpaRepository<Formule, Long> {
}
