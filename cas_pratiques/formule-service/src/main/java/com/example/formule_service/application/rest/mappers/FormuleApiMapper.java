package com.example.formule_service.application.rest.mappers;

import com.example.formule_service.application.rest.models.FormuleApi;
import com.example.formule_service.domain.models.Formule;
import org.springframework.stereotype.Component;

@Component
public class FormuleApiMapper {

  public FormuleApi convertirEnFormuleApi(Formule formule) {
    return new FormuleApi(formule.getId(), formule.getPrixDeBase(), formule.getNbrDeMois());
  }

  public Formule convertirEnFormule(FormuleApi formuleApi) {
    return new Formule(formuleApi.getId(), formuleApi.getPrixDeBase(), formuleApi.getNbrDeMois());
  }
}
