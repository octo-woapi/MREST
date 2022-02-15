package com.example.salle_de_sport.application.rest.models;

import java.util.Objects;

public class PeriodeApi {

    private Long id;
    private String dateDeDebut;
    private String dateDeFin;

    public PeriodeApi(Long id, String dateDeDebut, String dateDeFin) {
        this.id = id;
        this.dateDeDebut = dateDeDebut;
        this.dateDeFin = dateDeFin;
    }

    public Long getId() {
        return id;
    }

    public String getDateDeDebut() {
        return dateDeDebut;
    }

    public String getDateDeFin() {
        return dateDeFin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PeriodeApi)) return false;
        PeriodeApi that = (PeriodeApi) o;
        return getId().equals(that.getId()) && getDateDeDebut().equals(that.getDateDeDebut()) && getDateDeFin().equals(that.getDateDeFin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDateDeDebut(), getDateDeFin());
    }
}
