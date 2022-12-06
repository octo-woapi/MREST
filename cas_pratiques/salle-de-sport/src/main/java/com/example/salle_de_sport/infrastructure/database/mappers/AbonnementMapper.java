package com.example.salle_de_sport.infrastructure.database.mappers;

import com.example.salle_de_sport.domain.models.Abonnement;
import com.example.salle_de_sport.domain.models.Formule;
import com.example.salle_de_sport.domain.models.Periode;
import com.example.salle_de_sport.domain.usecases.RecupererUneFormule;
import com.example.salle_de_sport.infrastructure.database.entities.AbonnementEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AbonnementMapper {

  private final PeriodeMapper periodeMapper;
  private final RecupererUneFormule recupererUneFormule;


  public Abonnement convertirEnAbonnement(AbonnementEntity abonnementEntity) {
    List<Periode> periodes = abonnementEntity.getPeriodeEntities().stream()
        .map(this.periodeMapper::convertirEnPeriode)
        .collect(Collectors.toList());
    Formule formuleChoisie = recupererUneFormule.executer(abonnementEntity.getFormuleChoisieId());

    Abonnement abonnement = new Abonnement(
        abonnementEntity.getId(),
        abonnementEntity.getEmail(),
        abonnementEntity.estEtudiant(),
        abonnementEntity.getDateDeDebut(),
        formuleChoisie,
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
        abonnement.getFormuleChoisie().getId(),
        abonnement.getPrix(),
        abonnement.getPeriodes().stream().map(this.periodeMapper::convertirEnPeriodeEntity).toList());
  }
}
