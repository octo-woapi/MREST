package com.example.salle_de_sport.infrastructure.api.formule_service;

import com.example.salle_de_sport.domain.models.Formule;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FormuleApi {

  private Long id;
  private Double prixDeBase;
  private int nbrMois;

  public static Formule convertirEnFormule(FormuleApi formuleApi) {
    return new Formule(formuleApi.getId(), formuleApi.getPrixDeBase(), formuleApi.getNbrMois());
  }
}
