package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Abonnement;
import com.example.salle_de_sport.domain.models.Formule;
import com.example.salle_de_sport.domain.models.Periode;
import com.example.salle_de_sport.infrastructure.database.DatabaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.example.salle_de_sport.fixtures.AbonnementTestBuilder.unAbonnement;
import static com.example.salle_de_sport.fixtures.FormuleTestBuilder.uneFormule;
import static org.assertj.core.api.Assertions.assertThat;

class RenouvelerDesAbonnementsTest extends DatabaseRepositoryTest {

  @Autowired private CreerUnAbonnement creerUnAbonnement;
  @Autowired private RenouvelerDesAbonnements renouvelerDesAbonnements;

  @Test
  void executer_devrait_renvoyer_les_abonnements_renouveles_a_partir_de_la_date_fournie() {
    // Given
    String dateCible = "2022-07-01";
    Formule formule_6mois =
        creerUneFormuleEnBase(uneFormule().avecNbrDeMois(6).avecId(null).build());

    Abonnement abonnementARenouveler =
        creerUnAbonnement.executer(
            unAbonnement()
                .avecDateDeDebut("2022-01-01")
                .avecId(null)
                .avecFormule(formule_6mois)
                .build());
    creerUnAbonnement.executer(
        unAbonnement()
            .avecDateDeDebut("2022-05-01")
            .avecId(null)
            .avecFormule(formule_6mois)
            .build());

    // When
    List<Abonnement> resultat = renouvelerDesAbonnements.executer(dateCible);

    // Then
    assertThat(resultat).size().isEqualTo(1);

    Abonnement abonnementRenouvele = resultat.get(0);
    assertThat(abonnementRenouvele.getId()).isEqualTo(abonnementARenouveler.getId());

    List<Periode> periodes = abonnementRenouvele.getPeriodes();
    assertThat(periodes).size().isEqualTo(2);
    assertThat(periodes.get(1).getDateDeDebut()).isEqualTo("2022-07-01");
    assertThat(periodes.get(1).getDateDeFin()).isEqualTo("2022-12-31");
  }
}
