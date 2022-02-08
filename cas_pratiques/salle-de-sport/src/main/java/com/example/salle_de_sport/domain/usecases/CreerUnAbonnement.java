package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.exceptions.FormuleNonTrouveeException;
import com.example.salle_de_sport.domain.models.Abonnement;
import com.example.salle_de_sport.domain.models.Formule;
import com.example.salle_de_sport.domain.models.Periode;
import com.example.salle_de_sport.domain.models.Reduction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreerUnAbonnement {

  private final FormulePersistence formulePersistence;
  private final AbonnementPersistence abonnementPersistence;

  public CreerUnAbonnement(
      FormulePersistence formulePersistence, AbonnementPersistence abonnementPersistence) {
    this.formulePersistence = formulePersistence;
    this.abonnementPersistence = abonnementPersistence;
  }

  public Abonnement executer(final Abonnement abonnement) {
    Formule formule =
        formulePersistence
            .recupererUneFormule(abonnement.getFormuleChoisie().getId())
            .orElseThrow(
                () -> new FormuleNonTrouveeException(abonnement.getFormuleChoisie().getId()));

    List<Periode> periodes = new ArrayList<>();
    periodes.add(new Periode(abonnement.getDateDeDebut(), formule.getNbrDeMois()));

    Reduction reduction = new Reduction(abonnement.estEtudiant());
    abonnement.setPrix(reduction.calculer(formule.getPrixDeBase()));

    Abonnement nouvelAbonnement =
        new Abonnement(
            null,
            abonnement.getEmail(),
            abonnement.estEtudiant(),
            abonnement.getDateDeDebut(),
            formule,
            reduction.calculer(formule.getPrixDeBase()),
            periodes);

    return abonnementPersistence.creerUnAbonnement(nouvelAbonnement);
  }
}
