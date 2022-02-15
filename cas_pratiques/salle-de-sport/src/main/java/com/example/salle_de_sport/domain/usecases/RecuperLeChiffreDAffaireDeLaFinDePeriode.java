package com.example.salle_de_sport.domain.usecases;

import org.springframework.stereotype.Component;

@Component
public class RecuperLeChiffreDAffaireDeLaFinDePeriode {

  private final AbonnementPersistence abonnementPersistence;

  public RecuperLeChiffreDAffaireDeLaFinDePeriode(AbonnementPersistence abonnementPersistence) {
    this.abonnementPersistence = abonnementPersistence;
  }

  public double executer(String dateDeFinDeLaPeriode) {
    return abonnementPersistence.recupererTousLesAbonements().stream()
        .filter(abonnement -> abonnement.estEnCours(dateDeFinDeLaPeriode))
        .mapToDouble(abonnement -> abonnement.getPrix())
        .sum();
  }
}
