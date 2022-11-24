package com.example.formule_service.domain.models;

import java.util.Objects;

public class Formule {

  private Long id;
  private Double prixDeBase;
  private int nbrDeMois;

  public Formule() {}

  public Formule(Double prixDeBase, int nbrDeMois) {
    this.prixDeBase = prixDeBase;
    this.nbrDeMois = nbrDeMois;
  }

  public Formule(Long id, Double prixDeBase, int nbrDeMois) {
    this.id = id;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Formule)) return false;
    Formule formule = (Formule) o;
    return prixDeBase == formule.prixDeBase
        && nbrDeMois == formule.nbrDeMois
        && id.equals(formule.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, prixDeBase, nbrDeMois);
  }

  @Override
  public String toString() {
    return "Formule{"
        + "id="
        + id
        + ", prixDeBase="
        + prixDeBase
        + ", nbrDeMois="
        + nbrDeMois
        + '}';
  }
}
