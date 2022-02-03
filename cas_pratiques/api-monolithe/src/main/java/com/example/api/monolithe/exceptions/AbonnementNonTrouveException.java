package com.example.api.monolithe.exceptions;

public class AbonnementNonTrouveException extends RuntimeException {

    public AbonnementNonTrouveException(Long id) {
        super("Impossible de trouver l'abonnement " + id);
    }
}
