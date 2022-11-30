package com.exemple.formule_service_kafka.domain.usecases;

import com.exemple.formule_service_kafka.domain.models.Formule;
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
