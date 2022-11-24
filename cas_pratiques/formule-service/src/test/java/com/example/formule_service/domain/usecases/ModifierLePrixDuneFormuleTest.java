package com.example.formule_service.domain.usecases;

import com.example.formule_service.domain.models.Formule;
import com.example.formule_service.infrastructure.database.DatabaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.example.formule_service.fixtures.FormuleTestBuilder.uneFormule;
import static org.assertj.core.api.Assertions.assertThat;

class ModifierLePrixDuneFormuleTest extends DatabaseRepositoryTest {

  @Autowired private ModifierLePrixDuneFormule modifierLePrixDuneFormule;

  @Test
  void executer_devrait_renvoyer_la_formule_sauvegardee_avec_le_prix_modifie() {
    // Given
    double nouveauPrix = 100.0;
    Formule formule = creerUneFormuleEnBase(uneFormule().avecId(null).avecPrixDeBase(50.0).build());

    // When
    Formule resultat = modifierLePrixDuneFormule.executer(formule.getId(), nouveauPrix);

    // Then
    assertThat(resultat).isInstanceOf(Formule.class);
    assertThat(resultat.getId()).isEqualTo(formule.getId());
    assertThat(resultat.getPrixDeBase()).isEqualTo(nouveauPrix);
  }
}
