package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Formule;

import java.util.List;
import java.util.Optional;

public interface FormulePersistence {

    Optional<Formule> recupererUneFormule(Long id);

    List<Formule> recupererToutesLesFormules();

    Formule creerUneFormule(Formule formule);

    Formule modifierUneFormule(Formule formule);
}
