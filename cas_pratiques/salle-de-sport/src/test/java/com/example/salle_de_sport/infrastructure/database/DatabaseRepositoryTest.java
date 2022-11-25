package com.example.salle_de_sport.infrastructure.database;

import com.example.salle_de_sport.domain.models.Formule;
import com.example.salle_de_sport.infrastructure.api.formule_service.FormuleServicePersistanceWebClient;
import com.example.salle_de_sport.infrastructure.database.repositories.AbonnementRepository;
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

  @Autowired private AbonnementDatabaseJdbc abonnementDatabaseJdbc;
  @Autowired private FormuleServicePersistanceWebClient formuleServicePersistanceWebClient;

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
  }

  protected Formule creerUneFormule(Formule formule) {
    return formuleServicePersistanceWebClient.creerUneFormule(formule);
  }
}
