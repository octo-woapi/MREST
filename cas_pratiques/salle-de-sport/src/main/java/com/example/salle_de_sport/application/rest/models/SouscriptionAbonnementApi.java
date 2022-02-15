package com.example.salle_de_sport.application.rest.models;

import java.util.Objects;

public class SouscriptionAbonnementApi {

    private Long id;
    private String email;
    private AbonnementApi abonnementApi;

    public SouscriptionAbonnementApi(Long id, String email, AbonnementApi abonnementApi) {
        this.id = id;
        this.email = email;
        this.abonnementApi = abonnementApi;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public AbonnementApi getAbonnementApi() {
        return abonnementApi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SouscriptionAbonnementApi)) return false;
        SouscriptionAbonnementApi that = (SouscriptionAbonnementApi) o;
        return getId().equals(that.getId()) && getEmail().equals(that.getEmail()) && getAbonnementApi().equals(that.getAbonnementApi());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getAbonnementApi());
    }
}
