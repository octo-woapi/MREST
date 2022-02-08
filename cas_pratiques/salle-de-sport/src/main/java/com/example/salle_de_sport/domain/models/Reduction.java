package com.example.salle_de_sport.domain.models;

public final class Reduction {

  static final Double REDUCTION_ETUDIANT = 0.2;

  private final Double taux;

  public Reduction(final Boolean isEtudiant) {
    var tauxCalcule = 0.0;

    if (isEtudiant) {
      tauxCalcule += REDUCTION_ETUDIANT;
    }

    this.taux = tauxCalcule;
  }

  public Double calculer(final double prixDeBase) {
    Double prix = prixDeBase * (1 - this.taux);
    return prix;
  }
}
