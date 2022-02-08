package com.example.salle_de_sport.infrastructure.database.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class FormuleEntity {

    private @Id @GeneratedValue Long id;
    private Double prixDeBase;
    private int nbrDeMois;

    public FormuleEntity() {
    }

    public FormuleEntity(Long id, Double prixDeBase, int nbrDeMois) {
        this.id = id;
        this.prixDeBase = prixDeBase;
        this.nbrDeMois = nbrDeMois;
    }

    public Long getId() {
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
        return getPrixDeBase() == that.getPrixDeBase() && getNbrDeMois() == that.getNbrDeMois() && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPrixDeBase(), getNbrDeMois());
    }

    @Override
    public String toString() {
        return "FormuleEntity{" +
                "id=" + id +
                ", prixDeBase=" + prixDeBase +
                ", nbrDeMois=" + nbrDeMois +
                '}';
    }
}
