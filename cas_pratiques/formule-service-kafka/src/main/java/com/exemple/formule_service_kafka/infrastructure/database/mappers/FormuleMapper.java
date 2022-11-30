package com.exemple.formule_service_kafka.infrastructure.database.mappers;

import com.exemple.formule_service_kafka.domain.models.Formule;
import com.exemple.formule_service_kafka.infrastructure.database.entities.FormuleEntity;
import org.springframework.stereotype.Component;

@Component
public class FormuleMapper {

  public Formule convertirEnFormule(final FormuleEntity formuleEntity) {
    return new Formule(
        formuleEntity.getId(), formuleEntity.getPrixDeBase(), formuleEntity.getNbrDeMois());
  }

  public FormuleEntity convertirEnFormuleEntity(final Formule formule) {
    return new FormuleEntity(formule.getId(), formule.getPrixDeBase(), formule.getNbrDeMois());
  }
}
