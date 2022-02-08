package com.example.salle_de_sport.domain.models;

import java.util.Objects;

public class SouscriptionAbonnement {

  private Long id;
  private String email;
  private Abonnement abonnement;

  public SouscriptionAbonnement() {}

  public SouscriptionAbonnement(Long id, String email, Abonnement abonnement) {
    this.id = id;
    this.email = email;
    this.abonnement = abonnement;
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

  public Abonnement getAbonnement() {
    return abonnement;
  }

  public void setAbonnement(Abonnement abonnement) {
    this.abonnement = abonnement;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof SouscriptionAbonnement)) return false;
    SouscriptionAbonnement that = (SouscriptionAbonnement) o;
    return getId().equals(that.getId())
        && getEmail().equals(that.getEmail())
        && getAbonnement().equals(that.getAbonnement());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getEmail(), getAbonnement());
  }

  @Override
  public String toString() {
    return "SouscriptionAbonnement{"
        + "id="
        + id
        + ", email="
        + email
        + ", abonnement="
        + abonnement
        + '}';
  }
}
