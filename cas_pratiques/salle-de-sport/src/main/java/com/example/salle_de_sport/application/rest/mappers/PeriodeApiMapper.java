package com.example.salle_de_sport.application.rest.mappers;

import com.example.salle_de_sport.application.rest.models.PeriodeApi;
import com.example.salle_de_sport.domain.models.Periode;
import org.springframework.stereotype.Component;

@Component
public class PeriodeApiMapper {

    public PeriodeApi convertirEnPeriodeApi(Periode periode) {
        return new PeriodeApi(
                periode.getId(),
                periode.getDateDeDebut(),
                periode.getDateDeFin());
    }

    public Periode convertirEnPeriode(PeriodeApi periodeApi) {
        return new Periode(
                periodeApi.getId(),
                periodeApi.getDateDeDebut(),
                periodeApi.getDateDeFin());
    }
}
