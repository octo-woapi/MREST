package com.example.formule_service.domain.usecases;

import com.example.formule_service.domain.models.Formule;
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
