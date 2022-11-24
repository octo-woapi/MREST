package com.example.formule_service.domain.usecases;

import com.example.formule_service.domain.exceptions.FormuleNonTrouveeException;
import com.example.formule_service.domain.models.Formule;
import org.springframework.stereotype.Component;

@Component
public class ModifierLePrixDuneFormule {

    private final FormulePersistence formulePersistence;

    public ModifierLePrixDuneFormule(FormulePersistence formulePersistence) {
        this.formulePersistence = formulePersistence;
    }

    public Formule executer(Long id, Double nouveauPrix) {
        Formule formule = formulePersistence.recupererUneFormule(id)
                .orElseThrow(() -> new FormuleNonTrouveeException(id));

        formule.setPrixDeBase(nouveauPrix);
        return formulePersistence.modifierUneFormule(formule);
    }
}
