package com.example.api.monolithe.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public final class DatePersonnalisee {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private LocalDate date;

    public DatePersonnalisee() {
        this.date = LocalDate.now();
    }

    private DatePersonnalisee(LocalDate date) {
        this.date = date;
    }

    public DatePersonnalisee(String dateStr) {
        this.date = LocalDate.parse(dateStr, FORMATTER);
    }

    @Override
    public String toString() {
        return this.date.format(FORMATTER);
    }

    Boolean estApres(final DatePersonnalisee date) {
        return this.date.isAfter(date.date);
    }

    Boolean estApresOuEgale(final DatePersonnalisee date) {
        return estEgale(date) || this.date.isAfter(date.date);
    }

    Boolean estEgale(final DatePersonnalisee date) {
        return this.date.isEqual(date.date);
    }

    Boolean estAvant(final DatePersonnalisee dateDeFin) {
        return this.date.isBefore(dateDeFin.date);
    }

    DatePersonnalisee ajouterDesMois(final Integer nbMois) {
        return new DatePersonnalisee(
                this.date.plus(nbMois, ChronoUnit.MONTHS)
        );
    }

    DatePersonnalisee supprimerDesMois(final Integer nbMois) {
        return new DatePersonnalisee(
                this.date.minus(nbMois, ChronoUnit.MONTHS)
        );
    }

    DatePersonnalisee unJourAvant() {
        return new DatePersonnalisee(this.date.minusDays(1));
    }

    DatePersonnalisee unJourApres() {
        return new DatePersonnalisee(this.date.plusDays(1));
    }

    LocalDate toLocalDate() {
        return this.date;
    }
}
