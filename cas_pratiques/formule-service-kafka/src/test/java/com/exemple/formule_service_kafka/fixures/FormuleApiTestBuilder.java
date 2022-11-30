package com.exemple.formule_service_kafka.fixures;

import com.exemple.formule_service_kafka.application.rest.models.FormuleApi;
import com.exemple.formule_service_kafka.domain.models.Formule;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

public class FormuleApiTestBuilder {

  private Formule formule = new Formule();
  private JsonNodeUtil jsonNodeUtil;

  public static FormuleApiTestBuilder uneFormuleApi() {
    return new FormuleApiTestBuilder();
  }

  public FormuleApiTestBuilder avecFormule(Formule formule) {
    this.formule = formule;
    return this;
  }

  public FormuleApiTestBuilder avecJsonNode(JsonNode jsonNode) {
    this.jsonNodeUtil = new JsonNodeUtil(jsonNode);
    return this;
  }

  public FormuleApi build() {
    if (this.jsonNodeUtil != null) {
      return this.jsonNodeUtil.getFormuleApi();
    } else {
      return new FormuleApi(
          this.formule.getId(), this.formule.getPrixDeBase(), this.formule.getNbrDeMois());
    }
  }

  public List<FormuleApi> buildOf() {
    if (this.jsonNodeUtil != null) {
      return this.jsonNodeUtil.getListOfFormuleApis();
    } else {
      return List.of(this.build());
    }
  }
}
