package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Formule;
import com.example.salle_de_sport.infrastructure.database.DatabaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.example.salle_de_sport.fixtures.FormuleTestBuilder.uneFormule;
import static org.assertj.core.api.Assertions.assertThat;

class RecupererToutesLesFormulesTest extends DatabaseRepositoryTest {

  @Autowired private RecupererToutesLesFormules recupererToutesLesFormules;

  @Test
  void executer_devrait_renvoyer_toutes_les_formules_savegardees() {
    // Given
    Formule premiereFormule =
        creerUneFormuleEnBase(uneFormule().avecId(null).avecPrixDeBase(250.0).build());
    Formule deuxiemeFormule =
        creerUneFormuleEnBase(uneFormule().avecId(null).avecPrixDeBase(80.0).build());

    // When
    List<Formule> resultat = recupererToutesLesFormules.executer();

    // Then
    assertThat(resultat).isEqualTo(List.of(premiereFormule, deuxiemeFormule));
  }
}
