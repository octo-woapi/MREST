package com.example.api.monolithe.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Abonnement {

    private @Id @GeneratedValue Long id;
    private String email;
    private Boolean estEtudiant;
    private String dateDeDebut;

    @ManyToOne
    @JoinColumn(name = "formule_choisie_id")
    private Formule formuleChoisie;

    private String prix;

    @OneToMany(fetch= FetchType.LAZY, cascade= CascadeType.ALL)
    @JoinColumn(name="abonnement_id")
    private List<Periode> periodes = new ArrayList<>();

    public Abonnement(
            String email, boolean estEtudiant, String dateDeDebut, Formule formuleChoisie) {
        this.email = email;
        this.estEtudiant = estEtudiant;
        this.dateDeDebut = dateDeDebut;
        this.formuleChoisie = formuleChoisie;

        this.periodes.add(new Periode(dateDeDebut, formuleChoisie.getNbrMois()));

        Reduction reduction = new Reduction(estEtudiant);
        this.prix = reduction.calculer(formuleChoisie.getPrixDeBase());
    }

    public Abonnement(
            String email, boolean estEtudiant, String dateDeDebut, Formule formuleChoisie,
            String prix, List<Periode> periodes) {
        this.email = email;
        this.estEtudiant = estEtudiant;
        this.dateDeDebut = dateDeDebut;
        this.formuleChoisie = formuleChoisie;
        this.prix = prix;
        this.periodes = periodes;
    }

    public Boolean seraFiniALaDateDonnee(final String dateDeFin) {
        DatePersonnalisee datePersonnalisee = new DatePersonnalisee(dateDeFin);

        Periode lastPeriode = this.getDernierePeriode();
        return datePersonnalisee.estApres(new DatePersonnalisee(lastPeriode.getDateDeFin()));
    }

    public Boolean estEnCours(final String date) {
        Periode lastPeriode = this.getDernierePeriode();
        return lastPeriode.contient(date);
    }

    public Abonnement renouveller() {
        Periode lastPeriode = this.getDernierePeriode();
        this.periodes.add(this.getProchainePeriode(lastPeriode));
        return this;
    }

    private Periode getDernierePeriode() {
        return this.periodes.get(this.periodes.size() - 1);
    }

    private Periode getProchainePeriode(Periode periode) {
        DatePersonnalisee dateDeDebut = new DatePersonnalisee(periode.getDateDeDebut());
        DatePersonnalisee dateDeFin = new DatePersonnalisee(periode.getDateDeFin());

        var nbrDeMoisDansLaPeriodeCourante =
                Period.between(dateDeDebut.toLocalDate(),
                        dateDeFin.unJourApres().toLocalDate())
                        .getMonths();

        return new Periode(
                dateDeFin.unJourApres().toString(),
                nbrDeMoisDansLaPeriodeCourante
        );
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

    public Boolean getEstEtudiant() {
        return this.estEtudiant;
    }

    public void setEstEtudiant(Boolean estEtudiant) {
        this.estEtudiant = estEtudiant;
    }

    public String getDateDeDebut() {
        return this.dateDeDebut;
    }

    public void setDateDeDebut(String dateDeDebut) {
        this.dateDeDebut = dateDeDebut;
    }

    public String getPrix() {
        return this.prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public List<Periode> getPeriodes() {
        return this.periodes;
    }

    public void setPeriodes(List<Periode> periodes) {
        this.periodes = periodes;
    }

    public Formule getFormuleChoisie() {
        return this.formuleChoisie;
    }

    public void setFormuleChoisie(Formule formuleChoisie) {
        this.formuleChoisie = formuleChoisie;
    }

    public Boolean getEtudiant() {
        return this.estEtudiant;
    }

    public void setEtudiant(Boolean etudiant) {
        this.estEtudiant = etudiant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Abonnement that = (Abonnement) o;
        return Objects.equals(this.id, that.id)
                && Objects.equals(this.email, that.email)
                && Objects.equals(this.estEtudiant, that.estEtudiant)
                && Objects.equals(this.dateDeDebut, that.dateDeDebut)
                && Objects.equals(this.prix, that.prix)
                && Objects.equals(this.periodes, that.periodes)
                && Objects.equals(this.formuleChoisie, that.formuleChoisie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.email, this.estEtudiant, this.dateDeDebut, this.prix, this.periodes, this.formuleChoisie);
    }

    @Override
    public String toString() {
        return "Abonnement{" +
                "id=" + this.id +
                ", email='" + this.email + '\'' +
                ", estEtudiant=" + this.estEtudiant +
                ", dateDeDebut=" + this.dateDeDebut +
                ", prix=" + this.prix +
                ", periodes=" + this.periodes +
                ", formuleChoisie=" + this.formuleChoisie +
                '}';
    }
}
