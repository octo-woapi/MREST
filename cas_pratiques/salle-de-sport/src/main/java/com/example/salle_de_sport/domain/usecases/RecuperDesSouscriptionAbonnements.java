package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.SouscriptionAbonnement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecuperDesSouscriptionAbonnements {

  private final SouscriptionAbonnementPersistence souscriptionAbonnementPersistence;

  public RecuperDesSouscriptionAbonnements(
      SouscriptionAbonnementPersistence souscriptionAbonnementPersistence) {
    this.souscriptionAbonnementPersistence = souscriptionAbonnementPersistence;
  }

  public List<SouscriptionAbonnement> executer(Long abonnementId) {
    return souscriptionAbonnementPersistence.recuperDesSouscriptionAbonnementsParId(abonnementId);
  }
}
