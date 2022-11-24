package com.example.formule_service.infrastructure.database;

import com.example.formule_service.domain.models.Formule;
import com.example.formule_service.infrastructure.database.repositories.FormuleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public abstract class DatabaseRepositoryTest {

  @Autowired private FormuleDatabaseJdbc formuleDatabaseJdbc;
  @Autowired private FormuleRepository formuleRepository;


  @BeforeEach
  protected void setUp() {
    cleanAll();
  }

  @AfterEach
  public void tearDown() {
    cleanAll();
  }

  private void cleanAll() {
    formuleRepository.deleteAllInBatch();
  }

  protected Formule creerUneFormuleEnBase(Formule formule) {
    return formuleDatabaseJdbc.creerUneFormule(formule);
  }
}
