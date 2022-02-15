package com.example.salle_de_sport.fixtures;

import com.example.salle_de_sport.domain.models.Abonnement;
import com.example.salle_de_sport.domain.models.SouscriptionAbonnement;

public class SouscriptionAbonnementTestBuilder {

  private Long id = 1L;
  private String emailDeSouscription = "user@example.net";
  private Abonnement abonnement;

  public static SouscriptionAbonnementTestBuilder uneSouscriptionAbonnement() {
    return new SouscriptionAbonnementTestBuilder();
  }

  public SouscriptionAbonnementTestBuilder avecId(long id) {
    this.id = id;
    return this;
  }

  public SouscriptionAbonnementTestBuilder avecEmailDeSouscription(String emailDeSouscription) {
    this.emailDeSouscription = emailDeSouscription;
    return this;
  }

  public SouscriptionAbonnementTestBuilder avecAbonnement(Abonnement abonnement) {
    this.abonnement = abonnement;
    return this;
  }

  public SouscriptionAbonnement build() {
    return new SouscriptionAbonnement(this.id, this.emailDeSouscription, this.abonnement);
  }
}
