package com.example.formule_service.fixtures;

import com.example.formule_service.application.rest.models.FormuleApi;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public class JsonNodeUtil {

  private JsonNode jsonNode;

  public JsonNodeUtil(JsonNode jsonNode) {
    this.jsonNode = jsonNode;
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

  private FormuleApi toFormuleApiChoisie(JsonNode jsonNode) {
    return new FormuleApi(
        jsonNode.get("id").asLong(),
        jsonNode.get("prixDeBase").asDouble(),
        jsonNode.get("nbrMois").asInt());
  }

  private FormuleApi getFormuleApiFromJsonNode(final JsonNode jsonNode) {
    return new FormuleApi(
        jsonNode.get("id").asLong(),
        jsonNode.get("prixDeBase").asDouble(),
        jsonNode.get("nbrMois").asInt());
  }
}
