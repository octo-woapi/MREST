package com.exemple.formule_service_kafka.domain.usecases;

import static com.exemple.formule_service_kafka.fixures.FormuleTestBuilder.uneFormule;
import static org.assertj.core.api.Assertions.assertThat;

import com.exemple.formule_service_kafka.domain.models.Formule;
import com.exemple.formule_service_kafka.infrastructure.database.DatabaseRepositoryTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RecupererToutesLesFormulesITest extends DatabaseRepositoryTest {

  @Autowired private RecupererToutesLesFormules recupererToutesLesFormules;

  @Test
  void executer_devrait_renvoyer_toutes_les_formules_savegardees() {
    // Given
    Formule premiereFormule = creerUneFormuleEnBase(uneFormule().avecPrixDeBase(250.0).build());
    Formule deuxiemeFormule = creerUneFormuleEnBase(uneFormule().avecPrixDeBase(80.0).build());

    // When
    List<Formule> resultat = recupererToutesLesFormules.executer();

    // Then
    assertThat(resultat).isEqualTo(List.of(premiereFormule, deuxiemeFormule));
  }
}
