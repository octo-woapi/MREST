package com.example.salle_de_sport.application.rest.mappers;

import com.example.salle_de_sport.application.rest.models.AbonnementApi;
import com.example.salle_de_sport.domain.models.Abonnement;
import org.springframework.stereotype.Component;

@Component
public class AbonnementApiMapper {

  private final FormuleApiMapper formuleApiMapper;
  private final PeriodeApiMapper periodeApiMapper;

  public AbonnementApiMapper(FormuleApiMapper formuleApiMapper, PeriodeApiMapper periodeApiMapper) {
    this.formuleApiMapper = formuleApiMapper;
    this.periodeApiMapper = periodeApiMapper;
  }

  public AbonnementApi convertirEnAbonnementApi(Abonnement abonnement) {
    return new AbonnementApi(
        abonnement.getId(),
        abonnement.getEmail(),
        abonnement.getEstEtudiant(),
        abonnement.getDateDeDebut(),
        formuleApiMapper.convertirEnFormuleApi(abonnement.getFormuleChoisie()),
        abonnement.getPrix(),
        abonnement.getPeriodes().stream().map(periodeApiMapper::convertirEnPeriodeApi).toList());
  }

  public Abonnement convertirEnAbonnement(AbonnementApi abonnementApi) {
    return new Abonnement(
        abonnementApi.getId(),
        abonnementApi.getEmail(),
        abonnementApi.isEstEtudiant(),
        abonnementApi.getDateDeDebut(),
        formuleApiMapper.convertirEnFormule(abonnementApi.getFormuleApiChoisie()),
        abonnementApi.getPrix(),
        abonnementApi.getPeriodesApi().stream().map(periodeApiMapper::convertirEnPeriode).toList());
  }
}
