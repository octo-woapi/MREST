package com.example.salle_de_sport.fixtures;

import com.example.salle_de_sport.domain.models.Periode;

public class PeriodeTestBuilder {

  private Long id = 1L;
  private String dateDeDebut = "2022-01-01";
  private String dateDeFin = "2022-11-30";

  public static PeriodeTestBuilder unePeriode() {
    return new PeriodeTestBuilder();
  }

  public PeriodeTestBuilder avecId(Long id) {
    this.id = id;
    return this;
  }

  public PeriodeTestBuilder avecDateDeDebut(String dateDeDebut) {
    this.dateDeDebut = dateDeDebut;
    return this;
  }

  public PeriodeTestBuilder avecDateDeFin(String dateDeFin) {
    this.dateDeFin = dateDeFin;
    return this;
  }

  public Periode build() {
    return new Periode(this.id, this.dateDeDebut, this.dateDeFin);
  }
}
