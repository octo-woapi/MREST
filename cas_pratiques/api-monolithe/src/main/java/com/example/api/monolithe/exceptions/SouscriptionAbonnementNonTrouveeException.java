package com.example.api.monolithe.exceptions;

public class SouscriptionAbonnementNonTrouveeException extends RuntimeException {

    public SouscriptionAbonnementNonTrouveeException(Long id) {
        super("Impossible de trouver la souscription avec l'abonnement " + id);
    }
}
