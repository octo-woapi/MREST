package com.example.api.monolithe.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SouscriptionAbonnement {

    private @Id @GeneratedValue Long id;
    private String email;

    @ManyToOne
    @JoinColumn(name = "abonnement_id")
    private Abonnement abonnement;

    public SouscriptionAbonnement(String email, Abonnement abonnement) {
        this.email = email;
        this.abonnement = abonnement;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Abonnement getAbonnement() {
        return this.abonnement;
    }

    public void setAbonnement(Abonnement abonnement) {
        this.abonnement = abonnement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SouscriptionAbonnement that = (SouscriptionAbonnement) o;
        return this.id.equals(that.id)
                && this.email.equals(that.email)
                && this.abonnement.equals(that.abonnement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.email, this.abonnement);
    }

    @Override
    public String toString() {
        return "SouscriptionAbonnement{" +
                "id=" + this.id +
                ", email='" + this.email + '\'' +
                ", abonnement=" + this.abonnement +
                '}';
    }
}
