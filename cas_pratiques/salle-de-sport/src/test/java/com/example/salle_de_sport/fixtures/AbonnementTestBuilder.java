package com.example.salle_de_sport.fixtures;

import com.example.salle_de_sport.domain.models.Abonnement;
import com.example.salle_de_sport.domain.models.Formule;
import com.example.salle_de_sport.domain.models.Periode;

import java.util.ArrayList;
import java.util.List;

public class AbonnementTestBuilder {

  private Long id = 1l;
  private String email = "user@example.net";
  private boolean isEtudiant = false;
  private String dateDeDebut = "2022-01-01";
  private Double prix = 80.0;
  private Formule formule;
  List<Periode> periodes = new ArrayList<>();

  public AbonnementTestBuilder() {}

  public static AbonnementTestBuilder unAbonnement() {
    return new AbonnementTestBuilder();
  }

  public AbonnementTestBuilder avecId(Long id) {
    this.id = id;
    return this;
  }

  public AbonnementTestBuilder avecEmail(String email) {
    this.email = email;
    return this;
  }

  public AbonnementTestBuilder avecEtudiant(boolean isEtudiant) {
    this.isEtudiant = isEtudiant;
    return this;
  }

  public AbonnementTestBuilder avecDateDeDebut(String dateDeDebut) {
    this.dateDeDebut = dateDeDebut;
    return this;
  }

  public AbonnementTestBuilder avecFormule(Formule formule) {
    this.formule = formule;
    return this;
  }

  public AbonnementTestBuilder avecPeriodes(List<Periode> periodes) {
    this.periodes = periodes;
    return this;
  }

  public AbonnementTestBuilder avecPrix(Double prix) {
    this.prix = prix;
    return this;
  }

  public Abonnement build() {
    return new Abonnement(
        this.id,
        this.email,
        this.isEtudiant,
        this.dateDeDebut,
        this.formule,
        this.prix,
        this.periodes);
  }
}
