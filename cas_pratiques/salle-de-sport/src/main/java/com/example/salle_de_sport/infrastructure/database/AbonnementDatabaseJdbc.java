package com.example.salle_de_sport.infrastructure.database;

import com.example.salle_de_sport.domain.models.Abonnement;
import com.example.salle_de_sport.domain.usecases.AbonnementPersistence;
import com.example.salle_de_sport.infrastructure.database.entities.AbonnementEntity;
import com.example.salle_de_sport.infrastructure.database.mappers.AbonnementMapper;
import com.example.salle_de_sport.infrastructure.database.mappers.FormuleMapper;
import com.example.salle_de_sport.infrastructure.database.mappers.PeriodeMapper;
import com.example.salle_de_sport.infrastructure.database.repositories.AbonnementRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class AbonnementDatabaseJdbc implements AbonnementPersistence {

  private final AbonnementRepository abonnementRepository;
  private final AbonnementMapper abonnementMapper;
  private final FormuleMapper formuleMapper;
  private final PeriodeMapper periodeMapper;

  public AbonnementDatabaseJdbc(
      AbonnementRepository abonnementRepository,
      AbonnementMapper abonnementMapper,
      FormuleMapper formuleMapper,
      PeriodeMapper periodeMapper) {
    this.abonnementRepository = abonnementRepository;
    this.abonnementMapper = abonnementMapper;
    this.formuleMapper = formuleMapper;
    this.periodeMapper = periodeMapper;
  }

  @Override
  @Transactional
  public Optional<Abonnement> recupererUnAbonnement(Long id) {
    return abonnementRepository.findById(id)
        .map(abonnementMapper::convertirEnAbonnement);
  }

  @Override
  @Transactional
  public List<Abonnement> recupererTousLesAbonements() {
    return abonnementRepository.findAll().stream()
        .map(abonnementMapper::convertirEnAbonnement)
        .toList();
  }

  @Override
  @Transactional
  public Abonnement creerUnAbonnement(Abonnement abonnement) {
    return abonnementMapper.convertirEnAbonnement(
        abonnementRepository.save(
            new AbonnementEntity(
                abonnement.getEmail(),
                abonnement.getEstEtudiant(),
                abonnement.getDateDeDebut(),
                formuleMapper.convertirEnFormuleEntity(abonnement.getFormuleChoisie()),
                abonnement.getPrix(),
                abonnement.getPeriodes().stream()
                    .map(periodeMapper::convertirEnPeriodeEntity)
                    .toList())));
  }

  @Override
  public List<Abonnement> modifierDesAbonnements(List<Abonnement> abonnements) {
    return abonnementRepository
        .saveAll(abonnements.stream().map(abonnementMapper::convertirEnAbonnementEntity).toList())
        .stream()
        .map(abonnementMapper::convertirEnAbonnement)
        .toList();
  }
}