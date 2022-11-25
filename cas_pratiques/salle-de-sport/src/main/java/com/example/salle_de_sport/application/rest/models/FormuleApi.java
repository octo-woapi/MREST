package com.example.salle_de_sport.application.rest.models;

import java.util.Objects;

public class FormuleApi {

    private Long id;
    private Double prixDeBase;
    private int nbrDeMois;

    public FormuleApi(Long id, Double prixDeBase, int nbrDeMois) {
        this.id = id;
        this.prixDeBase = prixDeBase;
        this.nbrDeMois = nbrDeMois;
    }

    public Long getId() {
        return id;
    }

    public Double getPrixDeBase() {
        return prixDeBase;
    }

    public int getNbrDeMois() {
        return nbrDeMois;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FormuleApi)) return false;
        FormuleApi that = (FormuleApi) o;
        return getNbrDeMois() == that.getNbrDeMois() && getId().equals(that.getId()) && getPrixDeBase().equals(that.getPrixDeBase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPrixDeBase(), getNbrDeMois());
    }
}
