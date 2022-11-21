package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Formule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class RecupererToutesLesFormules {

    private final FormulePersistence formulePersistence;

    public List<Formule> executer() {
        return formulePersistence.recupererToutesLesFormules();
    }
}
