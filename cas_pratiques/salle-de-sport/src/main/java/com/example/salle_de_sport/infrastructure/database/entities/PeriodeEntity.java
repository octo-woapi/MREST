package com.example.salle_de_sport.infrastructure.database.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class PeriodeEntity {

  private @Id @GeneratedValue Long id;
  private String dateDeDebut;
  private String dateDeFin;

  public PeriodeEntity() {
  }

  public PeriodeEntity(Long id, String dateDeDebut, String dateDeFin) {
    this.id = id;
    this.dateDeDebut = dateDeDebut;
    this.dateDeFin = dateDeFin;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDateDeDebut() {
    return this.dateDeDebut;
  }

  public String getDateDeFin() {
    return this.dateDeFin;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PeriodeEntity)) return false;
    PeriodeEntity that = (PeriodeEntity) o;
    return getId().equals(that.getId())
        && getDateDeDebut().equals(that.getDateDeDebut())
        && getDateDeFin().equals(that.getDateDeFin());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getDateDeDebut(), getDateDeFin());
  }

  @Override
  public String toString() {
    return "PeriodeEntity{" +
            "id=" + id +
            ", dateDeDebut='" + dateDeDebut + '\'' +
            ", dateDeFin='" + dateDeFin + '\'' +
            '}';
  }
}
