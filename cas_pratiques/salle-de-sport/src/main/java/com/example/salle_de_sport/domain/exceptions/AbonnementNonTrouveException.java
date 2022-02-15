package com.example.salle_de_sport.domain.exceptions;

public class AbonnementNonTrouveException extends RuntimeException {

    public AbonnementNonTrouveException(Long id) {
        super("Impossible de trouver l'abonnement " + id);
    }
}
