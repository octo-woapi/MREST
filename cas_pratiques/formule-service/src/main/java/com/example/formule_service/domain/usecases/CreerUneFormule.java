package com.example.formule_service.domain.usecases;

import com.example.formule_service.domain.models.Formule;
import org.springframework.stereotype.Component;

@Component
public class CreerUneFormule {

    private final FormulePersistence formulePersistence;

    public CreerUneFormule(FormulePersistence formulePersistence) {
        this.formulePersistence = formulePersistence;
    }

    public Formule executer(Formule formule) {
        return formulePersistence.creerUneFormule(formule);
    }
}
