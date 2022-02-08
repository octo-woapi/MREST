package com.example.salle_de_sport.infrastructure.database.mappers;

import com.example.salle_de_sport.domain.models.Periode;
import com.example.salle_de_sport.infrastructure.database.entities.PeriodeEntity;
import org.springframework.stereotype.Component;

@Component
public class PeriodeMapper {

  public Periode convertirEnPeriode(PeriodeEntity periodeEntity) {
    return new Periode(
        periodeEntity.getId(), periodeEntity.getDateDeDebut(), periodeEntity.getDateDeFin());
  }

  public PeriodeEntity convertirEnPeriodeEntity(Periode periode) {
    return new PeriodeEntity(periode.getId(), periode.getDateDeDebut(), periode.getDateDeFin());
  }
}
