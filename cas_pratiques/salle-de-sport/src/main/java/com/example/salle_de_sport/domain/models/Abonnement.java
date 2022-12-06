package com.example.salle_de_sport.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class Abonnement {

  private Long id;
  private String email;
  @JsonProperty("estEtudiant")
  private Boolean estEtudiant;
  private String dateDeDebut;
  private Formule formuleChoisie;
  private Double prix;

  private List<Periode> periodes = new ArrayList<>();


  public Abonnement(String email, boolean estEtudiant, String dateDeDebut, Formule formuleChoisie) {
    this.email = email;
    this.estEtudiant = estEtudiant;
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
}