package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Abonnement;
import com.example.salle_de_sport.domain.models.Formule;
import com.example.salle_de_sport.domain.models.SouscriptionAbonnement;
import com.example.salle_de_sport.infrastructure.database.DatabaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.example.salle_de_sport.fixtures.AbonnementTestBuilder.unAbonnement;
import static com.example.salle_de_sport.fixtures.FormuleTestBuilder.uneFormule;
import static org.assertj.core.api.Assertions.assertThat;

class EnvoyerUnEmailDeSouscriptionTest extends DatabaseRepositoryTest {

  @Autowired private EnvoyerUnEmailDeSouscription envoyerUnEmailDeSouscription;
  @Autowired private CreerUnAbonnement creerUnAbonnement;

  @Test
  void executer_devrait_renvoyer_la_souscription_cree_et_sauvegardee() {
    // Given
    String email = "user+souscription@example.net";
    Formule formule = creerUneFormule(uneFormule().avecId(null).build());
    Abonnement abonnement =
        creerUnAbonnement.executer(unAbonnement().avecId(null).avecFormule(formule).build());

    // When
    SouscriptionAbonnement resultat =
        envoyerUnEmailDeSouscription.executer(abonnement.getId(), email);

    // Then
    assertThat(resultat).isInstanceOf(SouscriptionAbonnement.class);
    assertThat(resultat).hasNoNullFieldsOrProperties();
  }
}
