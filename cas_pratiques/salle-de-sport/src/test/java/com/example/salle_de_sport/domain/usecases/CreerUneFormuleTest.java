package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Formule;
import com.example.salle_de_sport.infrastructure.database.DatabaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.example.salle_de_sport.fixtures.FormuleTestBuilder.uneFormule;
import static org.assertj.core.api.Assertions.assertThat;

class CreerUneFormuleTest extends DatabaseRepositoryTest {

  @Autowired private CreerUneFormule creerUneFormule;

  @Test
  void executer_devrait_renvoyer_la_formule_creee_et_savegardee() {
    // Given
    Formule formule = uneFormule().avecId(null).build();

    // When
    Formule resultat = creerUneFormule.executer(formule);

    // Then
    assertThat(resultat).isInstanceOf(Formule.class);
    assertThat(resultat).hasNoNullFieldsOrProperties();
  }
}
