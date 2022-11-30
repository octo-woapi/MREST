package com.exemple.formule_service_kafka.fixures;

import com.exemple.formule_service_kafka.domain.models.Formule;
import java.util.UUID;

public class FormuleTestBuilder {

  private UUID id = UUID.randomUUID();
  private Double prixDeBase = 100.0;
  private int nbrDeMois = 12;

  public static FormuleTestBuilder uneFormule() {
    return new FormuleTestBuilder();
  }

  public FormuleTestBuilder avecId(UUID id) {
    this.id = id;
    return this;
  }

  public FormuleTestBuilder avecPrixDeBase(Double prixDeBase) {
    this.prixDeBase = prixDeBase;
    return this;
  }

  public FormuleTestBuilder avecNbrDeMois(int nbrDeMois) {
    this.nbrDeMois = nbrDeMois;
    return this;
  }

  public Formule build() {
    return new Formule(this.id, this.prixDeBase, this.nbrDeMois);
  }
}
