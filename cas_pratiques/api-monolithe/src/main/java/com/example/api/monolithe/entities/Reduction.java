package com.example.api.monolithe.entities;

final class Reduction {

  static final Double REDUCTION_ETUDIANT = 0.2;

  private final Double taux;

  Reduction(final Boolean isEtudiant) {
    var tauxCalcule = 0.0;

    if (isEtudiant) {
      tauxCalcule += REDUCTION_ETUDIANT;
    }

    this.taux = tauxCalcule;
  }

  public String calculer(final int prixDeBase) {
    Double prix = Double.valueOf(prixDeBase);
    prix = prix * (1 - this.taux);
    return prix.toString();
  }
}
