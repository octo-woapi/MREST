package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Abonnement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecupererTousLesAbonnements {

    private final AbonnementPersistence abonnementPersistence;

    public RecupererTousLesAbonnements(AbonnementPersistence abonnementPersistence) {
        this.abonnementPersistence = abonnementPersistence;
    }

    public List<Abonnement> executer() {
        return abonnementPersistence.recupererTousLesAbonements();
    }
}
