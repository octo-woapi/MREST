package com.example.salle_de_sport.fixtures;

import com.example.salle_de_sport.application.rest.models.AbonnementApi;
import com.example.salle_de_sport.application.rest.models.PeriodeApi;
import com.example.salle_de_sport.domain.models.Abonnement;
import com.example.salle_de_sport.domain.models.Periode;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class AbonnementApiTestBuilder {

  private Abonnement abonnement = new Abonnement();
  private JsonNodeUtil jsonNodeUtil;

  public static AbonnementApiTestBuilder unAbonnementApi() {
    return new AbonnementApiTestBuilder();
  }

  public AbonnementApiTestBuilder avecAbonnement(Abonnement abonnement) {
    this.abonnement = abonnement;
    return this;
  }

  public AbonnementApiTestBuilder avecJsonNode(JsonNode jsonNode) {
    this.jsonNodeUtil = new JsonNodeUtil(jsonNode);
    return this;
  }

  public AbonnementApi build() {
    if (this.jsonNodeUtil != null) {
      return this.jsonNodeUtil.getAbonnementApi();
    } else {
      return new AbonnementApi(
          this.abonnement.getId(),
          this.abonnement.getEmail(),
          this.abonnement.getEstEtudiant(),
          this.abonnement.getDateDeDebut(),
          FormuleApiTestBuilder.uneFormuleApi()
              .avecFormule(this.abonnement.getFormuleChoisie())
              .build(),
          this.abonnement.getPrix(),
          this.abonnement.getPeriodes().stream()
              .map(AbonnementApiTestBuilder::toPeriodeApi)
              .toList());
    }
  }

  public List<AbonnementApi> buildListOf() {
    if (this.jsonNodeUtil != null) {
      return this.jsonNodeUtil.getListOfAbonnementApis();
    } else {
      return List.of(this.build());
    }
  }

  private static PeriodeApi toPeriodeApi(Periode periode) {
    return PeriodeApiTestBuilder.unePeriodeApi().avecPeriode(periode).build();
  }
}
