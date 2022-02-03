package com.example.api.monolithe.helpers;

import com.fasterxml.jackson.databind.JsonNode;

public class Liens {

  private Lien self;
  private Lien formules;
  private Lien abonnements;

  public Liens(JsonNode jsonNode) {
    this.self = new Lien(jsonNode.at("/self/href").asText());
    this.formules = new Lien(jsonNode.at("/formules/href").asText());
    this.abonnements = new Lien(jsonNode.at("/abonnements/href").asText());
  }

  public Lien getSelf() {
    return this.self;
  }

  public Lien getFormules() {
    return this.formules;
  }

  public Lien getAbonnements() {
    return this.abonnements;
  }
}
