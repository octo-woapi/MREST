package com.example.salle_de_sport.fixtures;

import com.example.salle_de_sport.application.rest.models.AbonnementApi;
import com.example.salle_de_sport.application.rest.models.FormuleApi;
import com.example.salle_de_sport.application.rest.models.PeriodeApi;
import com.example.salle_de_sport.application.rest.models.SouscriptionAbonnementApi;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonNodeUtil {

  private JsonNode jsonNode;

  public JsonNodeUtil(JsonNode jsonNode) {
    this.jsonNode = jsonNode;
  }

  public AbonnementApi getAbonnementApi() {
    return this.getAbonnementApiFromJsonNode(this.jsonNode);
  }

  public List<AbonnementApi> getListOfAbonnementApis() {
    List<AbonnementApi> abonnementApis = new ArrayList<>();
    if (this.jsonNode != null) {
      this.jsonNode.forEach(
          jsonNode -> {
            abonnementApis.add(this.getAbonnementApiFromJsonNode(jsonNode));
          });
    }
    return abonnementApis;
  }

  public SouscriptionAbonnementApi getSouscriptionAbonnementApi() {
    return this.getSouscriptionAbonnementApiFromJsonNode(this.jsonNode);
  }

  public FormuleApi getFormuleApi() {
    return this.getFormuleApiFromJsonNode(this.jsonNode);
  }

  public List<FormuleApi> getListOfFormuleApis() {
    List<FormuleApi> formuleApis = new ArrayList<>();
    if (this.jsonNode != null) {
      this.jsonNode.forEach(
          jsonNode -> {
            formuleApis.add(this.getFormuleApiFromJsonNode(jsonNode));
          });
    }
    return formuleApis;
  }

  public List<SouscriptionAbonnementApi> getListOfSouscriptionAbonnementApis() {
    List<SouscriptionAbonnementApi> souscriptionAbonnementApis = new ArrayList<>();
    if (this.jsonNode != null) {
      this.jsonNode.forEach(
              jsonNode -> {
                souscriptionAbonnementApis.add(this.getSouscriptionAbonnementApiFromJsonNode(jsonNode));
              });
    }
    return souscriptionAbonnementApis;
  }

  private AbonnementApi getAbonnementApiFromJsonNode(final JsonNode jsonNode) {
    return new AbonnementApi(
        jsonNode.get("id").asLong(),
        jsonNode.get("email").asText(),
        jsonNode.get("isEtudiant").asBoolean(),
        jsonNode.get("dateDeDebut").asText(),
        this.toFormuleApiChoisie(jsonNode.get("formuleApiChoisie")),
        jsonNode.get("prix").asDouble(),
        this.toPeriodeApis(jsonNode.get("periodeApis")));
  }

  private FormuleApi toFormuleApiChoisie(JsonNode jsonNode) {
    return new FormuleApi(
        jsonNode.get("id").asLong(),
        jsonNode.get("prixDeBase").asDouble(),
        jsonNode.get("nbrMois").asInt());
  }

  private List<PeriodeApi> toPeriodeApis(JsonNode jsonNode) {
    List<PeriodeApi> periodeApis = new ArrayList<>();

    for (Iterator<JsonNode> it = jsonNode.elements(); it.hasNext(); ) {
      JsonNode item = it.next();
      periodeApis.add(
          new PeriodeApi(
              item.get("id").asLong(),
              item.get("dateDeDebut").asText(),
              item.get("dateDeFin").asText()));
    }

    return periodeApis;
  }

  private SouscriptionAbonnementApi getSouscriptionAbonnementApiFromJsonNode(
      final JsonNode jsonNode) {
    return new SouscriptionAbonnementApi(
        jsonNode.get("id").asLong(),
        jsonNode.get("email").asText(),
        this.getAbonnementApiFromJsonNode(jsonNode.get("abonnementApi")));
  }

  private FormuleApi getFormuleApiFromJsonNode(final JsonNode jsonNode) {
    return new FormuleApi(
        jsonNode.get("id").asLong(),
        jsonNode.get("prixDeBase").asDouble(),
        jsonNode.get("nbrMois").asInt());
  }
}
