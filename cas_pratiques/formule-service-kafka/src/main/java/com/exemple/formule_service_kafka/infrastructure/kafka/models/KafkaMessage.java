package com.exemple.formule_service_kafka.infrastructure.kafka.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KafkaMessage(
    @JsonProperty("message") String message, @JsonProperty("identifier") int identifier) {}
