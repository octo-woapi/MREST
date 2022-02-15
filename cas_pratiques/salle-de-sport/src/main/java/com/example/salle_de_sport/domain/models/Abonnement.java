package com.example.salle_de_sport.domain.models;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Abonnement {

  private Long id;
  private String email;
  private Boolean isEtudiant;
  private String dateDeDebut;
  private Formule formuleChoisie;
  private Double prix;

  private List<Periode> periodes = new ArrayList<>();

  public Abonnement() {}

  public Abonnement(
      Long id,
      String email,
      Boolean isEtudiant,
      String dateDeDebut,
      Formule formuleChoisie,
      Double prix,
      List<Periode> periodes) {
    this.id = id;
    this.email = email;
    this.isEtudiant = isEtudiant;
    this.dateDeDebut = dateDeDebut;
    this.formuleChoisie = formuleChoisie;
    this.prix = prix;
    this.periodes = periodes;
  }

  public Abonnement(String email, boolean isEtudiant, String dateDeDebut, Formule formuleChoisie) {
    this.email = email;
    this.isEtudiant = isEtudiant;
    this.dateDeDebut = dateDeDebut;
    this.formuleChoisie = formuleChoisie;
  }

  public boolean seraFiniALaDateDonnee(final String dateDeFin) {
    DatePersonnalisee datePersonnalisee = new DatePersonnalisee(dateDeFin);

    Periode dernierePeriode = this.getDernierePeriode();
    return datePersonnalisee.estApres(new DatePersonnalisee(dernierePeriode.getDateDeFin()));
  }

  public Abonnement renouveler() {
    Periode dernierePeriode = this.getDernierePeriode();
    Periode prochainePeriode = this.getProchainePeriode(dernierePeriode);

    this.periodes.add(prochainePeriode);
    return this;
  }

  public boolean estEnCours(final String date) {
    Periode dernierePeriode = this.getDernierePeriode();
    return dernierePeriode.contient(date);
  }

  private Periode getDernierePeriode() {
    return this.periodes.get(this.periodes.size() - 1);
  }

  private Periode getProchainePeriode(Periode periode) {
    DatePersonnalisee dateDeDebut = new DatePersonnalisee(periode.getDateDeDebut());
    DatePersonnalisee dateDeFin = new DatePersonnalisee(periode.getDateDeFin());

    var nbrDeMoisDansLaPeriodeCourante =
        Period.between(dateDeDebut.toLocalDate(), dateDeFin.unJourApres().toLocalDate())
            .getMonths();

    return new Periode(dateDeFin.unJourApres().toString(), nbrDeMoisDansLaPeriodeCourante);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Boolean estEtudiant() {
    return isEtudiant;
  }

  public String getDateDeDebut() {
    return dateDeDebut;
  }

  public Formule getFormuleChoisie() {
    return formuleChoisie;
  }

  public void setFormuleChoisie(Formule formuleChoisie) {
    this.formuleChoisie = formuleChoisie;
  }

  public Double getPrix() {
    return prix;
  }

  public void setPrix(Double prix) {
    this.prix = prix;
  }

  public List<Periode> getPeriodes() {
    return periodes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Abonnement)) return false;
    Abonnement that = (Abonnement) o;
    return getId().equals(that.getId())
        && getEmail().equals(that.getEmail())
        && isEtudiant.equals(that.estEtudiant())
        && getDateDeDebut().equals(that.getDateDeDebut())
        && getFormuleChoisie().equals(that.getFormuleChoisie())
        && getPrix().equals(that.getPrix())
        && getPeriodes().equals(that.getPeriodes());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getId(),
        getEmail(),
        estEtudiant(),
        getDateDeDebut(),
        getFormuleChoisie(),
        getPrix(),
        getPeriodes());
  }

  @Override
  public String toString() {
    return "Abonnement{"
        + "id="
        + id
        + ", email="
        + email
        + ", isEtudiant="
        + isEtudiant
        + ", dateDeDebut="
        + dateDeDebut
        + ", formuleChoisie="
        + formuleChoisie
        + ", prix="
        + prix
        + ", periodes="
        + periodes
        + '}';
  }
}
