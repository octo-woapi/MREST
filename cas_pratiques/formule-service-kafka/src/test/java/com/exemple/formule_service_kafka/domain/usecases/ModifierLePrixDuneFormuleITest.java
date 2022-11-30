package com.exemple.formule_service_kafka.domain.usecases;

import static com.exemple.formule_service_kafka.fixures.FormuleTestBuilder.uneFormule;
import static org.assertj.core.api.Assertions.assertThat;

import com.exemple.formule_service_kafka.domain.models.Formule;
import com.exemple.formule_service_kafka.infrastructure.database.DatabaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ModifierLePrixDuneFormuleITest extends DatabaseRepositoryTest {

  @Autowired private ModifierLePrixDuneFormule modifierLePrixDuneFormule;

  @Test
  void executer_devrait_renvoyer_la_formule_sauvegardee_avec_le_prix_modifie() {
    // Given
    double nouveauPrix = 100.0;
    Formule formule = creerUneFormuleEnBase(uneFormule().avecPrixDeBase(50.0).build());

    // When
    Formule resultat = modifierLePrixDuneFormule.executer(formule.getId(), nouveauPrix);

    // Then
    assertThat(resultat).isInstanceOf(Formule.class);
    assertThat(resultat.getId()).isEqualTo(formule.getId());
    assertThat(resultat.getPrixDeBase()).isEqualTo(nouveauPrix);
  }
}
