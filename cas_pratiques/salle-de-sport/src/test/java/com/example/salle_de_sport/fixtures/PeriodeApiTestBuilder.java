package com.example.salle_de_sport.fixtures;

import com.example.salle_de_sport.application.rest.models.PeriodeApi;
import com.example.salle_de_sport.domain.models.Periode;

public class PeriodeApiTestBuilder {

    private Periode periode = new Periode();

    public static PeriodeApiTestBuilder unePeriodeApi() {
        return new PeriodeApiTestBuilder();
    }

    public PeriodeApiTestBuilder avecPeriode(Periode periode) {
        this.periode = periode;
        return this;
    }

    public PeriodeApi build() {
        return new PeriodeApi(
          this.periode.getId(),
          this.periode.getDateDeDebut(),
          this.periode.getDateDeFin());
    }
}
