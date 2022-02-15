package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Formule;
import com.example.salle_de_sport.infrastructure.database.DatabaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.example.salle_de_sport.fixtures.FormuleTestBuilder.uneFormule;
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
