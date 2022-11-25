package com.example.formule_service.domain.usecases;

import com.example.formule_service.domain.exceptions.FormuleNonTrouveeException;
import com.example.formule_service.domain.models.Formule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ModifierLePrixDuneFormule {

    private final FormulePersistence formulePersistence;

    public Formule executer(Long id, Double nouveauPrix) {
        Formule formule = formulePersistence.recupererUneFormule(id)
                .orElseThrow(() -> new FormuleNonTrouveeException(id));

        formule.setPrixDeBase(nouveauPrix);
        return formulePersistence.modifierUneFormule(formule);
    }
}
