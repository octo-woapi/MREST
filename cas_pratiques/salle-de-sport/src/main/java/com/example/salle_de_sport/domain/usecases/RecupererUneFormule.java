package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.exceptions.FormuleNonTrouveeException;
import com.example.salle_de_sport.domain.models.Formule;
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
