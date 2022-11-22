package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Abonnement;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class RecupererTousLesAbonnements {

    private final AbonnementPersistence abonnementPersistence;

    public List<Abonnement> executer() {
        return abonnementPersistence.recupererTousLesAbonements();
    }
}
