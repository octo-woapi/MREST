package com.example.salle_de_sport.infrastructure.database.mappers;

import com.example.salle_de_sport.domain.models.Abonnement;
import com.example.salle_de_sport.domain.models.Periode;
import com.example.salle_de_sport.infrastructure.database.entities.AbonnementEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AbonnementMapper {

  private final FormuleMapper formuleMapper;
  private final PeriodeMapper periodeMapper;

  public AbonnementMapper(FormuleMapper formuleMapper, PeriodeMapper periodeMapper) {
    this.formuleMapper = formuleMapper;
    this.periodeMapper = periodeMapper;
  }

  public Abonnement convertirEnAbonnement(AbonnementEntity abonnementEntity) {
    List<Periode> periodes = abonnementEntity.getPeriodeEntities().stream()
        .map(this.periodeMapper::convertirEnPeriode)
        .collect(Collectors.toList());

    Abonnement abonnement = new Abonnement(
        abonnementEntity.getId(),
        abonnementEntity.getEmail(),
        abonnementEntity.estEtudiant(),
        abonnementEntity.getDateDeDebut(),
        this.formuleMapper.convertirEnFormule(abonnementEntity.getFormuleEntityChoisie()),
        abonnementEntity.getPrix(),
        periodes);
    return abonnement;
  }

  public AbonnementEntity convertirEnAbonnementEntity(Abonnement abonnement) {
    return new AbonnementEntity(
        abonnement.getId(),
        abonnement.getEmail(),
        abonnement.getEstEtudiant(),
        abonnement.getDateDeDebut(),
        this.formuleMapper.convertirEnFormuleEntity(abonnement.getFormuleChoisie()),
        abonnement.getPrix(),
        abonnement.getPeriodes().stream().map(this.periodeMapper::convertirEnPeriodeEntity).toList());
  }
}
