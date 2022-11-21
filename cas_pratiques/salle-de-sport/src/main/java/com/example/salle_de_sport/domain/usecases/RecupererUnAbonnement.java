package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.exceptions.AbonnementNonTrouveException;
import com.example.salle_de_sport.domain.models.Abonnement;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RecupererUnAbonnement {

    private final AbonnementPersistence abonnementPersistence;

    public Abonnement executer(Long id) {
        return abonnementPersistence.recupererUnAbonnement(id)
                .orElseThrow(() -> new AbonnementNonTrouveException(id));
    }
}
