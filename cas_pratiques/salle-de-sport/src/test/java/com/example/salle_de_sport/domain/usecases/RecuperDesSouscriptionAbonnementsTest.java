package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Abonnement;
import com.example.salle_de_sport.domain.models.Formule;
import com.example.salle_de_sport.domain.models.SouscriptionAbonnement;
import com.example.salle_de_sport.infrastructure.database.DatabaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.example.salle_de_sport.fixtures.AbonnementTestBuilder.unAbonnement;
import static com.example.salle_de_sport.fixtures.FormuleTestBuilder.uneFormule;
import static org.assertj.core.api.Assertions.assertThat;

class RecuperDesSouscriptionAbonnementsTest extends DatabaseRepositoryTest {

  @Autowired private CreerUnAbonnement creerUnAbonnement;
  @Autowired private EnvoyerUnEmailDeSouscription envoyerUnEmailDeSouscription;

  @Autowired private RecuperDesSouscriptionAbonnements recuperDesSouscriptionAbonnements;

  @Test
  void executer_devrait_renvoyer_les_souscriptions_liees_a_un_abonnement() {
    // Given
    String email = "user+souscription@example.net";
    Formule formule = creerUneFormule(uneFormule().avecId(null).build());
    Abonnement abonnement =
        creerUnAbonnement.executer(unAbonnement().avecId(null).avecFormule(formule).build());

    SouscriptionAbonnement souscriptionAbonnement =
        envoyerUnEmailDeSouscription.executer(abonnement.getId(), email);

    // When
    List<SouscriptionAbonnement> resultat =
        recuperDesSouscriptionAbonnements.executer(abonnement.getId());

    // Then
    assertThat(resultat).isEqualTo(List.of(souscriptionAbonnement));
  }
}
