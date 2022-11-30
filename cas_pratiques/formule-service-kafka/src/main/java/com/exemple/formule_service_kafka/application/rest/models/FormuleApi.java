package com.exemple.formule_service_kafka.application.rest.models;

import java.util.UUID;

public record FormuleApi(UUID id, Double prixDeBase, int nbrMois) {}
