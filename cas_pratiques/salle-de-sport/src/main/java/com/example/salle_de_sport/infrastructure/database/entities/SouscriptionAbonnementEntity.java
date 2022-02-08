package com.example.salle_de_sport.infrastructure.database.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class SouscriptionAbonnementEntity {

  private @Id @GeneratedValue Long id;
  private String email;

  @ManyToOne
  @JoinColumn(name = "abonnement_id")
  private AbonnementEntity abonnementEntity;

  public SouscriptionAbonnementEntity(String email, AbonnementEntity abonnementEntity) {
    this.email = email;
    this.abonnementEntity = abonnementEntity;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public AbonnementEntity getAbonnementEntity() {
    return this.abonnementEntity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof SouscriptionAbonnementEntity)) return false;
    SouscriptionAbonnementEntity that = (SouscriptionAbonnementEntity) o;
    return getId().equals(that.getId())
        && getEmail().equals(that.getEmail())
        && getAbonnementEntity().equals(that.getAbonnementEntity());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getEmail(), getAbonnementEntity());
  }

  @Override
  public String toString() {
    return "SouscriptionAbonnementEntity{" +
            "id=" + id +
            ", email='" + email + '\'' +
            ", abonnementEntity=" + abonnementEntity +
            '}';
  }
}
