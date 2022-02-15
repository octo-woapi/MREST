package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Abonnement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RenouvelerDesAbonnements {

  private final AbonnementPersistence abonnementPersistence;

  public RenouvelerDesAbonnements(AbonnementPersistence abonnementPersistence) {
    this.abonnementPersistence = abonnementPersistence;
  }

  public List<Abonnement> executer(String dateDeRenouvellement) {
    List<Abonnement> abonnementsARenouveles =
        abonnementPersistence.recupererTousLesAbonements().stream()
            .filter(abonnement -> abonnement.seraFiniALaDateDonnee(dateDeRenouvellement))
            .map(Abonnement::renouveler)
            .toList();

    return abonnementPersistence.modifierDesAbonnements(abonnementsARenouveles);
  }
}
