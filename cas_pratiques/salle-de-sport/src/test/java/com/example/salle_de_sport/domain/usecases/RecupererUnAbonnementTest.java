package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Abonnement;
import com.example.salle_de_sport.domain.models.Formule;
import com.example.salle_de_sport.infrastructure.database.DatabaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.example.salle_de_sport.fixtures.AbonnementTestBuilder.unAbonnement;
import static com.example.salle_de_sport.fixtures.FormuleTestBuilder.uneFormule;
import static org.assertj.core.api.Assertions.assertThat;

class RecupererUnAbonnementTest extends DatabaseRepositoryTest {

  @Autowired private RecupererUnAbonnement recupererUnAbonnement;
  @Autowired private CreerUnAbonnement creerUnAbonnement;

  @Test
  void executer_devrait_renvoyer_l_abonnement_avec_l_id_fourni() {
    // Given
    Formule formule = creerUneFormuleEnBase(uneFormule().avecId(null).build());
    Abonnement abonnement =
        creerUnAbonnement.executer(unAbonnement().avecId(null).avecFormule(formule).build());

    // When
    Abonnement resultat = recupererUnAbonnement.executer(abonnement.getId());

    // Then
    assertThat(resultat).isInstanceOf(Abonnement.class);
    assertThat(resultat).isEqualTo(abonnement);
  }
}
