package com.example.salle_de_sport.fixtures;

import com.example.salle_de_sport.application.rest.models.SouscriptionAbonnementApi;
import com.example.salle_de_sport.domain.models.SouscriptionAbonnement;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class SouscriptionAbonnementApiTestBuilder {

  private Long id = 1L;
  private SouscriptionAbonnement souscriptionAbonnement = new SouscriptionAbonnement();
  private JsonNodeUtil jsonNodeUtil;

  public static SouscriptionAbonnementApiTestBuilder uneSouscriptionAbonnementApi() {
    return new SouscriptionAbonnementApiTestBuilder();
  }

  public SouscriptionAbonnementApiTestBuilder avecId(long id) {
    this.id = id;
    return this;
  }

  public SouscriptionAbonnementApiTestBuilder avecSouscriptionAbonnement(
      SouscriptionAbonnement souscriptionAbonnement) {
    this.souscriptionAbonnement = souscriptionAbonnement;
    return this;
  }

  public SouscriptionAbonnementApiTestBuilder avecJsonNode(JsonNode jsonNode) {
    this.jsonNodeUtil = new JsonNodeUtil(jsonNode);
    return this;
  }

  public SouscriptionAbonnementApi build() {
    if (this.jsonNodeUtil != null) {
      return this.jsonNodeUtil.getSouscriptionAbonnementApi();
    } else {
      return new SouscriptionAbonnementApi(
          this.id,
          this.souscriptionAbonnement.getEmail(),
          AbonnementApiTestBuilder.unAbonnementApi()
              .avecAbonnement(this.souscriptionAbonnement.getAbonnement())
              .build());
    }
  }

  public List<SouscriptionAbonnementApi> buildOf() {
    if (this.jsonNodeUtil != null) {
      return this.jsonNodeUtil.getListOfSouscriptionAbonnementApis();
    } else {
      return List.of(this.build());
    }
  }
}
