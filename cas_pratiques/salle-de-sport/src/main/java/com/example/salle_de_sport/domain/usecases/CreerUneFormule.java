package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Formule;
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
