package com.example.formule_service.domain.usecases;

import com.example.formule_service.domain.exceptions.FormuleNonTrouveeException;
import com.example.formule_service.domain.models.Formule;
import org.springframework.stereotype.Component;

@Component
public class RecupererUneFormule {

    private final FormulePersistence formulePersistence;

    public RecupererUneFormule(FormulePersistence formulePersistence) {
        this.formulePersistence = formulePersistence;
    }

    public Formule executer(Long id) {
        return formulePersistence.recupererUneFormule(id)
                .orElseThrow(() -> new FormuleNonTrouveeException(id));
    }
}
