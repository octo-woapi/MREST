package com.exemple.formule_service_kafka.domain.usecases;

import com.exemple.formule_service_kafka.domain.models.Formule;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FormulePersistence {

  Optional<Formule> recupererUneFormule(UUID id);

  List<Formule> recupererToutesLesFormules();

  Formule creerUneFormule(Formule formule);

  Formule modifierUneFormule(Formule formule);
}
