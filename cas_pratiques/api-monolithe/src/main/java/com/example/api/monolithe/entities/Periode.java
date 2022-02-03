package com.example.api.monolithe.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Periode {

    private @Id @GeneratedValue Long id;
    private String dateDeDebut;
    private String dateDeFin;

    public Periode(String dateDeDebut, int nbMois) {
        this.dateDeDebut = dateDeDebut;

        DatePersonnalisee datePersonnalisee = new DatePersonnalisee(dateDeDebut);
        this.dateDeFin = datePersonnalisee.ajouterDesMois(nbMois).unJourAvant().toString();
    }

    public Periode(String dateDeDebut, String dateDeFin) {
        this.dateDeDebut = dateDeDebut;
        this.dateDeFin = dateDeFin;
    }

    public Periode(String periode) {
        var strAU = " au ";
        this.dateDeDebut = periode.split(strAU)[0];
        this.dateDeFin = periode.split(strAU)[1];
    }

    public Boolean contient(final String date) {
        DatePersonnalisee dateDeReference = new DatePersonnalisee(date);
        return dateDeReference.estApresOuEgale(new DatePersonnalisee(this.dateDeDebut))
                && dateDeReference.estAvant(new DatePersonnalisee(this.dateDeFin));
    }


    public String getDateDeDebut() {
        return this.dateDeDebut;
    }

    public void setDateDeDebut(String dateDeDebut) {
        this.dateDeDebut = dateDeDebut;
    }

    public String getDateDeFin() {
        return this.dateDeFin;
    }

    public void setDateDeFin(String dateDeFin) {
        this.dateDeFin = dateDeFin;
    }

    @Override
    public String toString() {
        return String.format("%s au %s", this.dateDeDebut, this.dateDeFin);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Periode periode = (Periode) o;
        return Objects.equals(this.dateDeDebut, periode.dateDeDebut) && Objects.equals(this.dateDeFin, periode.dateDeFin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.dateDeDebut, this.dateDeFin);
    }
}
