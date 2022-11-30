package com.exemple.formule_service_kafka.domain.usecases;

import com.exemple.formule_service_kafka.domain.exceptions.FormuleNonTrouveeException;
import com.exemple.formule_service_kafka.domain.models.Formule;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ModifierLePrixDuneFormule {

  private final FormulePersistence formulePersistence;

  public Formule executer(UUID id, Double nouveauPrix) {
    Formule formule =
        formulePersistence
            .recupererUneFormule(id)
            .orElseThrow(() -> new FormuleNonTrouveeException(id));

    formule.setPrixDeBase(nouveauPrix);
    return formulePersistence.modifierUneFormule(formule);
  }
}
