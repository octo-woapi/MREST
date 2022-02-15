package com.example.salle_de_sport.application.rest.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AbonnementApi {

    private Long id;
    private String email;
    private boolean isEtudiant;
    private String dateDeDebut;
    private FormuleApi formuleApiChoisie;
    private Double prix;

    private List<PeriodeApi> periodeApis = new ArrayList<>();

    public AbonnementApi() {
    }

    public AbonnementApi(Long id, String email, boolean isEtudiant, String dateDeDebut, FormuleApi formuleApiChoisie, Double prix, List<PeriodeApi> periodeApis) {
        this.id = id;
        this.email = email;
        this.isEtudiant = isEtudiant;
        this.dateDeDebut = dateDeDebut;
        this.formuleApiChoisie = formuleApiChoisie;
        this.prix = prix;
        this.periodeApis = periodeApis;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public boolean getIsEtudiant() {
        return isEtudiant;
    }

    public String getDateDeDebut() {
        return dateDeDebut;
    }

    public FormuleApi getFormuleApiChoisie() {
        return formuleApiChoisie;
    }

    public Double getPrix() {
        return prix;
    }

    public List<PeriodeApi> getPeriodeApis() {
        return periodeApis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbonnementApi)) return false;
        AbonnementApi that = (AbonnementApi) o;
        return isEtudiant == that.isEtudiant && getId().equals(that.getId()) && getEmail().equals(that.getEmail()) && getDateDeDebut().equals(that.getDateDeDebut()) && getFormuleApiChoisie().equals(that.getFormuleApiChoisie()) && getPrix().equals(that.getPrix()) && getPeriodeApis().equals(that.getPeriodeApis());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), isEtudiant, getDateDeDebut(), getFormuleApiChoisie(), getPrix(), getPeriodeApis());
    }
}
