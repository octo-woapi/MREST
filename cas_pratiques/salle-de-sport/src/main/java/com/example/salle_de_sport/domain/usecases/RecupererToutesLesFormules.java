package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Formule;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecupererToutesLesFormules {

    private final FormulePersistence formulePersistence;

    public RecupererToutesLesFormules(FormulePersistence formulePersistence) {
        this.formulePersistence = formulePersistence;
    }

    public List<Formule> executer() {
        return formulePersistence.recupererToutesLesFormules();
    }
}
