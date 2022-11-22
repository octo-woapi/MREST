package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.exceptions.FormuleNonTrouveeException;
import com.example.salle_de_sport.domain.models.Formule;
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
