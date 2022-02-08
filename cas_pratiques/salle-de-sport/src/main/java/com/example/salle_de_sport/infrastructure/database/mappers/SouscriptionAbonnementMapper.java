package com.example.salle_de_sport.infrastructure.database.mappers;

import com.example.salle_de_sport.domain.models.SouscriptionAbonnement;
import com.example.salle_de_sport.infrastructure.database.entities.AbonnementEntity;
import com.example.salle_de_sport.infrastructure.database.entities.SouscriptionAbonnementEntity;
import org.springframework.stereotype.Component;

@Component
public class SouscriptionAbonnementMapper {

  private final AbonnementMapper abonnementMapper;

  public SouscriptionAbonnementMapper(AbonnementMapper abonnementMapper) {
    this.abonnementMapper = abonnementMapper;
  }

  public SouscriptionAbonnementEntity convertirEnSouscriptionAbonnementEntity(
      String email, AbonnementEntity abonnementEntity) {
    return new SouscriptionAbonnementEntity(email, abonnementEntity);
  }

  public SouscriptionAbonnement convertirEnSouscriptionAbonnement(
      SouscriptionAbonnementEntity souscriptionAbonnementEntity) {
    return new SouscriptionAbonnement(
        souscriptionAbonnementEntity.getId(),
        souscriptionAbonnementEntity.getEmail(),
        abonnementMapper.convertirEnAbonnement(souscriptionAbonnementEntity.getAbonnementEntity()));
  }
}
