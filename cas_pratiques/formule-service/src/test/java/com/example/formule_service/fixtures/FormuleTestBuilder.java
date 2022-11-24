package com.example.formule_service.fixtures;

import com.example.formule_service.domain.models.Formule;

public class FormuleTestBuilder {

    private Long id = 1L;
    private Double prixDeBase = 100.0;
    private int nbrDeMois = 12;

    public static FormuleTestBuilder uneFormule() {
        return new FormuleTestBuilder();
    }

    public FormuleTestBuilder avecId(Long id) {
        this.id = id;
        return this;
    }

    public FormuleTestBuilder avecPrixDeBase(Double prixDeBase) {
        this.prixDeBase = prixDeBase;
        return this;
    }

    public FormuleTestBuilder avecNbrDeMois(int nbrDeMois) {
        this.nbrDeMois = nbrDeMois;
        return this;
    }

    public Formule build() {
        return new Formule(this.id, this.prixDeBase, this.nbrDeMois);
    }
}
