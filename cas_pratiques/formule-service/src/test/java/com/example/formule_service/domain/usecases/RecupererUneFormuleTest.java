package com.example.formule_service.domain.usecases;

import com.example.formule_service.domain.models.Formule;
import com.example.formule_service.infrastructure.database.DatabaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.example.formule_service.fixtures.FormuleTestBuilder.uneFormule;
import static org.assertj.core.api.Assertions.assertThat;

class RecupererUneFormuleTest extends DatabaseRepositoryTest {

  @Autowired private RecupererUneFormule recupererUneFormule;

  @Test
  void executer_devrait_renvoyer_la_formule_avec_l_id_fourni() {
    // Given
    Formule formule = creerUneFormuleEnBase(uneFormule().avecId(null).build());

    // When
    Formule resultat = recupererUneFormule.executer(formule.getId());

    // Then
    assertThat(resultat).isInstanceOf(Formule.class);
    assertThat(resultat).isEqualTo(formule);
  }
}
