package com.example.salle_de_sport.application.rest.mappers;

import com.example.salle_de_sport.application.rest.models.FormuleApi;
import com.example.salle_de_sport.domain.models.Formule;
import org.springframework.stereotype.Component;

@Component
public class FormuleApiMapper {

  public FormuleApi convertirEnFormuleApi(Formule formule) {
    return new FormuleApi(formule.getId(), formule.getPrixDeBase(), formule.getNbrDeMois());
  }

  public Formule convertirEnFormule(FormuleApi formuleApi) {
    return new Formule(formuleApi.getId(), formuleApi.getPrixDeBase(), formuleApi.getNbrMois());
  }
}
