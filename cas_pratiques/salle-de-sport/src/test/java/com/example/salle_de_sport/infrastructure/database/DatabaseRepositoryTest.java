package com.example.salle_de_sport.infrastructure.database;

import com.example.salle_de_sport.domain.models.Formule;
import com.example.salle_de_sport.infrastructure.database.repositories.AbonnementRepository;
import com.example.salle_de_sport.infrastructure.database.repositories.FormuleRepository;
import com.example.salle_de_sport.infrastructure.database.repositories.PeriodeRepository;
import com.example.salle_de_sport.infrastructure.database.repositories.SouscriptionAbonnementRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public abstract class DatabaseRepositoryTest {

  @Autowired private FormuleDatabaseJdbc formuleDatabaseJdbc;
  @Autowired private AbonnementDatabaseJdbc abonnementDatabaseJdbc;

  @Autowired private FormuleRepository formuleRepository;
  @Autowired private AbonnementRepository abonnementRepository;
  @Autowired private PeriodeRepository periodeRepository;
  @Autowired private SouscriptionAbonnementRepository souscriptionAbonnementRepository;

  @BeforeEach
  protected void setUp() {
    cleanAll();
  }

  @AfterEach
  public void tearDown() {
    cleanAll();
  }

  private void cleanAll() {
    souscriptionAbonnementRepository.deleteAllInBatch();
    periodeRepository.deleteAllInBatch();
    abonnementRepository.deleteAllInBatch();
    formuleRepository.deleteAllInBatch();
  }

  protected Formule creerUneFormuleEnBase(Formule formule) {
    return formuleDatabaseJdbc.creerUneFormule(formule);
  }
}
