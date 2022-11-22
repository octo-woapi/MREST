package com.example.salle_de_sport.domain.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Periode {

  private Long id;
  private String dateDeDebut;
  private String dateDeFin;

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
}