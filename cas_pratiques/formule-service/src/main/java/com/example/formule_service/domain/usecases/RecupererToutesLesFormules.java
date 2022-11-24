package com.example.formule_service.domain.usecases;

import com.example.formule_service.domain.models.Formule;
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
