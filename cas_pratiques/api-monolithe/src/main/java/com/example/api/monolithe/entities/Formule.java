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
public class Formule {

    private @Id @GeneratedValue Long id;
    private int prixDeBase;
    private int nbrMois;

    public Formule(int prixDeBase, int nbrMois) {
        this.prixDeBase = prixDeBase;
        this.nbrMois = nbrMois;
    }

    public Long getId() {
        return this.id;
    }

    public int getPrixDeBase() {
        return this.prixDeBase;
    }

    public void setPrixDeBase(int prixDeBase) {
        this.prixDeBase = prixDeBase;
    }

    public int getNbrMois() {
        return this.nbrMois;
    }

    public String getDescription() {
        return String.format("Formule %s mois à %s euros", this.nbrMois, this.prixDeBase);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Formule formule = (Formule) o;
        return this.prixDeBase == formule.prixDeBase && this.nbrMois == formule.nbrMois && Objects.equals(this.id, formule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.prixDeBase, this.nbrMois);
    }

    @Override
    public String toString() {
        return "Formule{" +
                "id=" + this.id +
                ", prixDeBase=" + this.prixDeBase +
                ", nbrMois=" + this.nbrMois +
                '}';
    }
}
