package com.example.salle_de_sport.infrastructure.database;

import com.example.salle_de_sport.domain.models.Abonnement;
import com.example.salle_de_sport.domain.models.SouscriptionAbonnement;
import com.example.salle_de_sport.domain.usecases.SouscriptionAbonnementPersistence;
import com.example.salle_de_sport.infrastructure.database.mappers.AbonnementMapper;
import com.example.salle_de_sport.infrastructure.database.mappers.SouscriptionAbonnementMapper;
import com.example.salle_de_sport.infrastructure.database.repositories.SouscriptionAbonnementRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SouscriptionAbonnementDatabaseJdbc implements SouscriptionAbonnementPersistence {

  private final SouscriptionAbonnementRepository souscriptionAbonnementRepository;
  private final SouscriptionAbonnementMapper souscriptionAbonnementMapper;
  private final AbonnementMapper abonnementMapper;

  public SouscriptionAbonnementDatabaseJdbc(
      SouscriptionAbonnementRepository souscriptionAbonnementRepository,
      SouscriptionAbonnementMapper souscriptionAbonnementMapper,
      AbonnementMapper abonnementMapper) {
    this.souscriptionAbonnementRepository = souscriptionAbonnementRepository;
    this.souscriptionAbonnementMapper = souscriptionAbonnementMapper;
    this.abonnementMapper = abonnementMapper;
  }

  @Override
  public SouscriptionAbonnement creerUneSouscriptionAbonnement(
      String email, Abonnement abonnement) {
    return souscriptionAbonnementMapper.convertirEnSouscriptionAbonnement(
        souscriptionAbonnementRepository.save(
            souscriptionAbonnementMapper.convertirEnSouscriptionAbonnementEntity(
                email, abonnementMapper.convertirEnAbonnementEntity(abonnement))));
  }

  @Override
  public List<SouscriptionAbonnement> recuperDesSouscriptionAbonnementsParId(Long abonnementId) {
    return souscriptionAbonnementRepository.findAllByAbonnementId(abonnementId).stream()
        .map(souscriptionAbonnementMapper::convertirEnSouscriptionAbonnement)
        .toList();
  }
}
