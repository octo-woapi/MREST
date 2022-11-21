package com.example.salle_de_sport.domain.models;

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

  private Long id;
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
