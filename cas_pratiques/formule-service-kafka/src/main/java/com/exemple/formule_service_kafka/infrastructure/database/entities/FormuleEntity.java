package com.exemple.formule_service_kafka.infrastructure.database.entities;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "formules")
public class FormuleEntity {

  private @Id UUID id;
  private Double prixDeBase;
  private int nbrDeMois;

  public FormuleEntity() {}

  public FormuleEntity(UUID id, Double prixDeBase, int nbrDeMois) {
    this.id = id;
    this.prixDeBase = prixDeBase;
    this.nbrDeMois = nbrDeMois;
  }

  public UUID getId() {
    return this.id;
  }

  public Double getPrixDeBase() {
    return this.prixDeBase;
  }

  public int getNbrDeMois() {
    return this.nbrDeMois;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof FormuleEntity)) return false;
    FormuleEntity that = (FormuleEntity) o;
    return getPrixDeBase() == that.getPrixDeBase()
        && getNbrDeMois() == that.getNbrDeMois()
        && getId().equals(that.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getPrixDeBase(), getNbrDeMois());
  }

  @Override
  public String toString() {
    return "FormuleEntity{"
        + "id="
        + id
        + ", prixDeBase="
        + prixDeBase
        + ", nbrDeMois="
        + nbrDeMois
        + '}';
  }
}
