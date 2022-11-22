package com.example.salle_de_sport.domain.usecases;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RecuperLeChiffreDAffaireDeLaFinDePeriode {

  private final AbonnementPersistence abonnementPersistence;

  public double executer(String dateDeFinDeLaPeriode) {
    return abonnementPersistence.recupererTousLesAbonements().stream()
        .filter(abonnement -> abonnement.estEnCours(dateDeFinDeLaPeriode))
        .mapToDouble(abonnement -> abonnement.getPrix())
        .sum();
  }
}
