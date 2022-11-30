package com.exemple.formule_service_kafka.domain.usecases;

import static com.exemple.formule_service_kafka.fixures.FormuleTestBuilder.uneFormule;
import static org.assertj.core.api.Assertions.assertThat;

import com.exemple.formule_service_kafka.domain.models.Formule;
import com.exemple.formule_service_kafka.infrastructure.database.DatabaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CreerUneFormuleITest extends DatabaseRepositoryTest {

  @Autowired private CreerUneFormule creerUneFormule;

  @Test
  void executer_devrait_renvoyer_la_formule_creee_et_savegardee() {
    // Given
    Formule formule = uneFormule().build();

    // When
    Formule resultat = creerUneFormule.executer(formule);

    // Then
    assertThat(resultat).isInstanceOf(Formule.class);
    assertThat(resultat).hasNoNullFieldsOrProperties();
  }
}
