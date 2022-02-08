package com.example.salle_de_sport.domain.models;

import java.util.Objects;

public class Periode {

  private Long id;
  private String dateDeDebut;
  private String dateDeFin;

  public Periode() {}

  public Periode(Long id, String dateDeDebut, String dateDeFin) {
    this.id = id;
    this.dateDeDebut = dateDeDebut;
    this.dateDeFin = dateDeFin;
  }

  public Periode(String dateDeDebut, int nbrDeMois) {
    this.dateDeDebut = dateDeDebut;

    DatePersonnalisee datePersonnalisee = new DatePersonnalisee(dateDeDebut);
    this.dateDeFin = datePersonnalisee.ajouterDesMois(nbrDeMois).unJourAvant().toString();
  }

  public boolean contient(final String date) {
    DatePersonnalisee dateDeReference = new DatePersonnalisee(date);
    return dateDeReference.estApresOuEgale(new DatePersonnalisee(this.dateDeDebut))
        && dateDeReference.estAvant(new DatePersonnalisee(this.dateDeFin));
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDateDeDebut() {
    return this.dateDeDebut;
  }

  public String getDateDeFin() {
    return this.dateDeFin;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Periode)) return false;
    Periode periode = (Periode) o;
    return Objects.equals(getId(), periode.getId())
        && getDateDeDebut().equals(periode.getDateDeDebut())
        && getDateDeFin().equals(periode.getDateDeFin());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getDateDeDebut(), getDateDeFin());
  }

  @Override
  public String toString() {
    return "Periode{"
        + "id="
        + id
        + ", dateDeDebut="
        + dateDeDebut
        + ", dateDeFin="
        + dateDeFin
        + '}';
  }
}
