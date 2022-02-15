package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.exceptions.AbonnementNonTrouveException;
import com.example.salle_de_sport.domain.models.Abonnement;
import com.example.salle_de_sport.domain.models.SouscriptionAbonnement;
import org.springframework.stereotype.Component;

@Component
public class EnvoyerUnEmailDeSouscription {

  private final AbonnementPersistence abonnementPersistence;
  private final SouscriptionAbonnementPersistence souscriptionAbonnementPersistence;
  private final MessageriePersistence messageriePersistence;

  public EnvoyerUnEmailDeSouscription(
      AbonnementPersistence abonnementPersistence,
      SouscriptionAbonnementPersistence souscriptionAbonnementPersistence,
      MessageriePersistence messageriePersistence) {
    this.abonnementPersistence = abonnementPersistence;
    this.souscriptionAbonnementPersistence = souscriptionAbonnementPersistence;
    this.messageriePersistence = messageriePersistence;
  }

  public SouscriptionAbonnement executer(Long id, String emailAUtiliser) {
    Abonnement abonnement =
        abonnementPersistence
            .recupererUnAbonnement(id)
            .orElseThrow(() -> new AbonnementNonTrouveException(id));

    messageriePersistence.envoyerUnEmail(
        emailAUtiliser,
        "Bienvenu(e) chez CraftGym, profite bien de ton abonnement %s."
            .formatted(abonnement.getFormuleChoisie().getDescription()));

    return souscriptionAbonnementPersistence.creerUneSouscriptionAbonnement(
        emailAUtiliser, abonnement);
  }
}
