package com.example.salle_de_sport.domain.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@EqualsAndHashCode
public final class DatePersonnalisee {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

  private LocalDate date;


  public DatePersonnalisee(String dateStr) {
    this.date = LocalDate.parse(dateStr, FORMATTER);
  }

  public Boolean estApres(final DatePersonnalisee date) {
    return this.date.isAfter(date.date);
  }

  public Boolean estApresOuEgale(final DatePersonnalisee date) {
    return estEgale(date) || this.date.isAfter(date.date);
  }

  public Boolean estEgale(final DatePersonnalisee date) {
    return this.date.isEqual(date.date);
  }

  public Boolean estAvant(final DatePersonnalisee dateDeFin) {
    return this.date.isBefore(dateDeFin.date);
  }

  public DatePersonnalisee ajouterDesMois(final Integer nbMois) {
    return new DatePersonnalisee(this.date.plus(nbMois, ChronoUnit.MONTHS));
  }

  public DatePersonnalisee unJourAvant() {
    return new DatePersonnalisee(this.date.minusDays(1));
  }

  public DatePersonnalisee unJourApres() {
    return new DatePersonnalisee(this.date.plusDays(1));
  }

  public LocalDate toLocalDate() {
    return this.date;
  }

  @Override
  public String toString() {
    return this.date.format(FORMATTER);
  }
}
