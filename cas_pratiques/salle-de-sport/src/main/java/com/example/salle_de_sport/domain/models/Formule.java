package com.example.salle_de_sport.domain.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Double getPrixDeBase() {
    return prixDeBase;
  }

  public void setPrixDeBase(Double prixDeBase) {
    this.prixDeBase = prixDeBase;
  }

  public int getNbrDeMois() {
    return nbrDeMois;
  }

}
