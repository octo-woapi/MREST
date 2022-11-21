package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.SouscriptionAbonnement;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class RecuperDesSouscriptionAbonnements {

  private final SouscriptionAbonnementPersistence souscriptionAbonnementPersistence;

  public List<SouscriptionAbonnement> executer(Long abonnementId) {
    return souscriptionAbonnementPersistence.recuperDesSouscriptionAbonnementsParId(abonnementId);
  }
}
