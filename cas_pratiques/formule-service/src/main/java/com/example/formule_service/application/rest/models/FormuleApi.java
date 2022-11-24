package com.example.formule_service.application.rest.models;

import java.util.Objects;

public class FormuleApi {

    private Long id;
    private Double prixDeBase;
    private int nbrMois;

    public FormuleApi(Long id, Double prixDeBase, int nbrMois) {
        this.id = id;
        this.prixDeBase = prixDeBase;
        this.nbrMois = nbrMois;
    }

    public Long getId() {
        return id;
    }

    public Double getPrixDeBase() {
        return prixDeBase;
    }

    public int getNbrMois() {
        return nbrMois;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FormuleApi)) return false;
        FormuleApi that = (FormuleApi) o;
        return getNbrMois() == that.getNbrMois() && getId().equals(that.getId()) && getPrixDeBase().equals(that.getPrixDeBase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPrixDeBase(), getNbrMois());
    }
}
