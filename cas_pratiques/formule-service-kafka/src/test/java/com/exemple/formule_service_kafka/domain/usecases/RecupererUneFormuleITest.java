package com.exemple.formule_service_kafka.domain.usecases;

import static com.exemple.formule_service_kafka.fixures.FormuleTestBuilder.uneFormule;
import static org.assertj.core.api.Assertions.assertThat;

import com.exemple.formule_service_kafka.domain.models.Formule;
import com.exemple.formule_service_kafka.infrastructure.database.DatabaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RecupererUneFormuleITest extends DatabaseRepositoryTest {

  @Autowired private RecupererUneFormule recupererUneFormule;

  @Test
  void executer_devrait_renvoyer_la_formule_avec_l_id_fourni() {
    // Given
    Formule formule = creerUneFormuleEnBase(uneFormule().build());

    // When
    Formule resultat = recupererUneFormule.executer(formule.getId());

    // Then
    assertThat(resultat).isInstanceOf(Formule.class);
    assertThat(resultat).isEqualTo(formule);
  }
}
