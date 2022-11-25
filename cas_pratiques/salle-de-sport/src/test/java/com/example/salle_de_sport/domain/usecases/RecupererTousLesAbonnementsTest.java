package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Abonnement;
import com.example.salle_de_sport.domain.models.Formule;
import com.example.salle_de_sport.infrastructure.database.DatabaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.example.salle_de_sport.fixtures.AbonnementTestBuilder.unAbonnement;
import static com.example.salle_de_sport.fixtures.FormuleTestBuilder.uneFormule;
import static org.assertj.core.api.Assertions.assertThat;

class RecupererTousLesAbonnementsTest extends DatabaseRepositoryTest {

  @Autowired private RecupererTousLesAbonnements recupererTousLesAbonnements;
  @Autowired CreerUnAbonnement creerUnAbonnement;

  @Test
  void executer_devrait_renvoyer_tous_les_abonnements_savegardees() {
    // Given
    Formule formule = creerUneFormule(uneFormule().avecId(null).build());

    Abonnement premierAbonnement =
        creerUnAbonnement.executer(unAbonnement().avecId(null).avecFormule(formule).build());
    Abonnement deuxiemeAbonnement =
        creerUnAbonnement.executer(unAbonnement().avecId(null).avecFormule(formule).build());

    // When
    List<Abonnement> resultat = recupererTousLesAbonnements.executer();

    // Then
    assertThat(resultat).isEqualTo(List.of(premierAbonnement, deuxiemeAbonnement));
  }
}
