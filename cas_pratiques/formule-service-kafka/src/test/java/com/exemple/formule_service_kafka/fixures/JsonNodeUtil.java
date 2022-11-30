package com.exemple.formule_service_kafka.fixures;

import com.exemple.formule_service_kafka.application.rest.models.FormuleApi;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JsonNodeUtil {

  private JsonNode jsonNode;

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

  private FormuleApi getFormuleApiFromJsonNode(final JsonNode jsonNode) {
    return new FormuleApi(
        UUID.fromString(jsonNode.get("id").asText()),
        jsonNode.get("prixDeBase").asDouble(),
        jsonNode.get("nbrMois").asInt());
  }
}
