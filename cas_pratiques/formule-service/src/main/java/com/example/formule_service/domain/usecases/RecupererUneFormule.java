package com.example.formule_service.domain.usecases;

import com.example.formule_service.domain.exceptions.FormuleNonTrouveeException;
import com.example.formule_service.domain.models.Formule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RecupererUneFormule {

    private final FormulePersistence formulePersistence;

    public Formule executer(Long id) {
        return formulePersistence.recupererUneFormule(id)
                .orElseThrow(() -> new FormuleNonTrouveeException(id));
    }
}
