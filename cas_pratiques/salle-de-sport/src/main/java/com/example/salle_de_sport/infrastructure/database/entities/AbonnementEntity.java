package com.example.salle_de_sport.infrastructure.database.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class AbonnementEntity {

    private @Id @GeneratedValue Long id;
    private String email;
    private Boolean isEtudiant;
    private String dateDeDebut;
    private Double prix;
    private Long formuleChoisieId;

    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="abonnement_id")
    private List<PeriodeEntity> periodeEntities = new ArrayList<>();

    public AbonnementEntity() {
    }

    public AbonnementEntity(
            String email, boolean isEtudiant, String dateDeDebut, Long formuleChoisieId,
            Double prix, List<PeriodeEntity> periodeEntities) {
        this.email = email;
        this.isEtudiant = isEtudiant;
        this.dateDeDebut = dateDeDebut;
        this.formuleChoisieId = formuleChoisieId;
        this.prix = prix;

        periodeEntities.forEach(periodeEntity -> this.periodeEntities.add(periodeEntity));
    }

    public AbonnementEntity(
            Long id, String email, boolean isEtudiant, String dateDeDebut,
            Long formuleChoisieId,
            Double prix, List<PeriodeEntity> periodeEntities) {
        this.id = id;
        this.email = email;
        this.isEtudiant = isEtudiant;
        this.dateDeDebut = dateDeDebut;
        this.formuleChoisieId = formuleChoisieId;
        this.prix = prix;

        periodeEntities.forEach(periodeEntity -> this.periodeEntities.add(periodeEntity));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean estEtudiant() {
        return this.isEtudiant;
    }

    public String getDateDeDebut() {
        return this.dateDeDebut;
    }

    public Double getPrix() {
        return this.prix;
    }

    public List<PeriodeEntity> getPeriodeEntities() {
        return this.periodeEntities;
    }

    public Long getFormuleChoisieId() {
        return this.formuleChoisieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbonnementEntity)) return false;
        AbonnementEntity that = (AbonnementEntity) o;
        return getId().equals(that.getId()) && getEmail().equals(that.getEmail()) && isEtudiant.equals(that.estEtudiant())
                && getDateDeDebut().equals(that.getDateDeDebut()) && getFormuleChoisieId().equals(that.getFormuleChoisieId()) && getPrix().equals(that.getPrix()) && getPeriodeEntities().equals(that.getPeriodeEntities());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), isEtudiant, getDateDeDebut(), getFormuleChoisieId(), getPrix(), getPeriodeEntities());
    }

    @Override
    public String toString() {
        return "AbonnementEntity{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", isEtudiant=" + isEtudiant +
                ", dateDeDebut='" + dateDeDebut + '\'' +
                ", prix='" + prix + '\'' +
                ", formuleEntityChoisie=" + formuleChoisieId +
                ", periodeEntities=" + this.periodeEntities +
                '}';
    }
}
