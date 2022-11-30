package com.exemple.formule_service_kafka.domain.models;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Formule {

  private UUID id;
  private Double prixDeBase;
  private int nbrDeMois;

  public Formule(Double prixDeBase, int nbrDeMois) {
    this.prixDeBase = prixDeBase;
    this.nbrDeMois = nbrDeMois;
  }

  public String getDescription() {
    return String.format("Formule %s mois à %s euros", this.nbrDeMois, this.prixDeBase);
  }
}
