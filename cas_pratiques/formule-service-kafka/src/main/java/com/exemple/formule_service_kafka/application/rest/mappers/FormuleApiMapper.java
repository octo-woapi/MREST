package com.exemple.formule_service_kafka.application.rest.mappers;

import com.exemple.formule_service_kafka.application.rest.models.FormuleApi;
import com.exemple.formule_service_kafka.domain.models.Formule;
import org.springframework.stereotype.Component;

@Component
public class FormuleApiMapper {

  public FormuleApi convertirEnFormuleApi(Formule formule) {
    return new FormuleApi(formule.getId(), formule.getPrixDeBase(), formule.getNbrDeMois());
  }

  public Formule convertirEnFormule(FormuleApi formuleApi) {
    return new Formule(formuleApi.id(), formuleApi.prixDeBase(), formuleApi.nbrMois());
  }
}
