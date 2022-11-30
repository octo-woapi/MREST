package com.exemple.formule_service_kafka.domain.exceptions;

import java.util.UUID;

public class FormuleNonTrouveeException extends RuntimeException {

  public FormuleNonTrouveeException(UUID id) {
    super("Impossible de trouver la formule " + id);
  }
}
