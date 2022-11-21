package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Abonnement;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class RenouvelerDesAbonnements {

  private final AbonnementPersistence abonnementPersistence;

  public List<Abonnement> executer(String dateDeRenouvellement) {
    List<Abonnement> abonnementsARenouveles =
        abonnementPersistence.recupererTousLesAbonements().stream()
            .filter(abonnement -> abonnement.seraFiniALaDateDonnee(dateDeRenouvellement))
            .map(Abonnement::renouveler)
            .toList();

    return abonnementPersistence.modifierDesAbonnements(abonnementsARenouveles);
  }
}
