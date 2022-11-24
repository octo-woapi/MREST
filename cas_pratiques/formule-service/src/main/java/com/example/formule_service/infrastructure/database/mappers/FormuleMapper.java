package com.example.formule_service.infrastructure.database.mappers;

import com.example.formule_service.infrastructure.database.entities.FormuleEntity;
import com.example.formule_service.domain.models.Formule;
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
