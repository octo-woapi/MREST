package com.example.salle_de_sport.domain.exceptions;

public class SouscriptionAbonnementNonTrouveeException extends RuntimeException {

    public SouscriptionAbonnementNonTrouveeException(Long id) {
        super("Impossible de trouver la souscription avec l'abonnement " + id);
    }
}
