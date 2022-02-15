package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Abonnement;
import com.example.salle_de_sport.domain.models.Formule;
import com.example.salle_de_sport.infrastructure.database.DatabaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.example.salle_de_sport.fixtures.AbonnementTestBuilder.unAbonnement;
import static com.example.salle_de_sport.fixtures.FormuleTestBuilder.uneFormule;
import static org.assertj.core.api.Assertions.assertThat;

class CreerUnAbonnementTest extends DatabaseRepositoryTest {

  @Autowired private CreerUnAbonnement creerUnAbonnement;

  @Test
  void executer_devrait_renvoyer_l_abonnement_cree_et_sauvgarde() {
    // Given
    Formule formule = creerUneFormuleEnBase(uneFormule().avecId(null).build());
    Abonnement abonnement = unAbonnement().avecId(null).avecFormule(formule).build();

    // When
    Abonnement resultat = creerUnAbonnement.executer(abonnement);

    // Then
    assertThat(resultat).isInstanceOf(Abonnement.class);
    assertThat(resultat).hasNoNullFieldsOrProperties();
  }
}
