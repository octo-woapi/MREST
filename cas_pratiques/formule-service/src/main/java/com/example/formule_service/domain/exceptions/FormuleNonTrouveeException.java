package com.example.formule_service.domain.exceptions;

public class FormuleNonTrouveeException extends RuntimeException {

    public FormuleNonTrouveeException(Long id) {
        super("Impossible de trouver la formule " + id);
    }
}
