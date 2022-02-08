package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Abonnement;

import java.util.List;
import java.util.Optional;

public interface AbonnementPersistence {

    Optional<Abonnement> recupererUnAbonnement(Long id);

    List<Abonnement> recupererTousLesAbonements();

    Abonnement creerUnAbonnement(Abonnement abonnement);

    List<Abonnement> modifierDesAbonnements(List<Abonnement> abonnements);
}
