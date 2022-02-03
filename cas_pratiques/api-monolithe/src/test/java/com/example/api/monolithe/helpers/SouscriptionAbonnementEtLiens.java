package com.example.api.monolithe.helpers;

import com.example.api.monolithe.entities.Abonnement;
import com.example.api.monolithe.entities.Formule;
import com.example.api.monolithe.entities.Periode;
import com.example.api.monolithe.entities.SouscriptionAbonnement;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SouscriptionAbonnementEtLiens {

  private SouscriptionAbonnement souscriptionAbonnement;
  private Liens liens;

  public SouscriptionAbonnementEtLiens(JsonNode jsonNode) {
    this.souscriptionAbonnement =
        new SouscriptionAbonnement(
            jsonNode.get("id").asLong(),
            jsonNode.get("email").asText(),
            toAbonnement(jsonNode.get("abonnement")));
    this.souscriptionAbonnement.setId(jsonNode.get("id").asLong());

    this.liens = new Liens(jsonNode.get("_links"));
  }

  private Abonnement toAbonnement(JsonNode jsonNode) {
    Abonnement abonnement =
        new Abonnement(
            jsonNode.get("email").asText(),
            jsonNode.get("estEtudiant").asBoolean(),
            jsonNode.get("dateDeDebut").asText(),
            toFormuleChoisie(jsonNode.get("formuleChoisie")),
            jsonNode.get("prix").asText(),
            toPeriodes(jsonNode.get("periodes")));
    abonnement.setId(jsonNode.get("id").asLong());

    return abonnement;
  }

  private List<Periode> toPeriodes(JsonNode jsonNode) {
    List<Periode> periodes = new ArrayList<>();

    for (Iterator<JsonNode> it = jsonNode.elements(); it.hasNext(); ) {
      JsonNode item = it.next();
      periodes.add(new Periode(item.get("dateDeDebut").asText(), item.get("dateDeFin").asText()));
    }

    return periodes;
  }

  private Formule toFormuleChoisie(JsonNode jsonNode) {
    return new Formule(
            jsonNode.get("id").asLong(),
            jsonNode.get("prixDeBase").asInt(),
            jsonNode.get("nbrMois").asInt());
  }

  public SouscriptionAbonnement getSouscriptionAbonnement() {
    return souscriptionAbonnement;
  }

  public Liens getLiens() {
    return liens;
  }
}
