package com.exemple.formule_service_kafka.domain.usecases;

import com.exemple.formule_service_kafka.domain.models.Formule;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RecupererToutesLesFormules {

  private final FormulePersistence formulePersistence;

  public List<Formule> executer() {
    return formulePersistence.recupererToutesLesFormules();
  }
}
