package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Abonnement;
import com.example.salle_de_sport.domain.models.SouscriptionAbonnement;

import java.util.List;

public interface SouscriptionAbonnementPersistence {

    SouscriptionAbonnement creerUneSouscriptionAbonnement(String email, Abonnement abonnement);

    List<SouscriptionAbonnement> recuperDesSouscriptionAbonnementsParId(Long abonnementId);
}
