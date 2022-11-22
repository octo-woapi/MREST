package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Formule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreerUneFormule {

    private final FormulePersistence formulePersistence;

    public Formule executer(Formule formule) {
        return formulePersistence.creerUneFormule(formule);
    }
}
