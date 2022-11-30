package com.exemple.formule_service_kafka.infrastructure.database;

import com.exemple.formule_service_kafka.domain.models.Formule;
import com.exemple.formule_service_kafka.infrastructure.database.repositories.FormuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public abstract class DatabaseRepositoryTest {

  @Autowired private FormuleDatabaseJdbc formuleDatabaseJdbc;
  @Autowired private FormuleRepository formuleRepository;

  @BeforeEach
  protected void setUp() {
    cleanAll();
  }

  private void cleanAll() {
    formuleRepository.deleteAllInBatch();
  }

  protected Formule creerUneFormuleEnBase(Formule formule) {
    return formuleDatabaseJdbc.creerUneFormule(formule);
  }
}
