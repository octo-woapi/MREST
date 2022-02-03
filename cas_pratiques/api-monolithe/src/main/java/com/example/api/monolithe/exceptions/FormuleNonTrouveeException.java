package com.example.api.monolithe.exceptions;

public class FormuleNonTrouveeException extends RuntimeException {

    public FormuleNonTrouveeException(Long id) {
        super("Impossible de trouver la formule " + id);
    }
}
