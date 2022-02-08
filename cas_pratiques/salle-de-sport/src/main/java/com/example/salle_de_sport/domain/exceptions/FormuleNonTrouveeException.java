package com.example.salle_de_sport.domain.exceptions;

public class FormuleNonTrouveeException extends RuntimeException {

    public FormuleNonTrouveeException(Long id) {
        super("Impossible de trouver la formule " + id);
    }
}
