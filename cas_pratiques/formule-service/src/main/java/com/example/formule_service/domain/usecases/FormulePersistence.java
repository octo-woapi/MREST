package com.example.formule_service.domain.usecases;

import com.example.formule_service.domain.models.Formule;

import java.util.List;
import java.util.Optional;

public interface FormulePersistence {

    Optional<Formule> recupererUneFormule(Long id);

    List<Formule> recupererToutesLesFormules();

    Formule creerUneFormule(Formule formule);

    Formule modifierUneFormule(Formule formule);
}
