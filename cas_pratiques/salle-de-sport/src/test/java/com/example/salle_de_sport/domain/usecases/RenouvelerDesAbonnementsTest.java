package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Abonnement;
import com.example.salle_de_sport.domain.models.Formule;
import com.example.salle_de_sport.domain.models.Periode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.salle_de_sport.fixtures.AbonnementTestBuilder.unAbonnement;
import static com.example.salle_de_sport.fixtures.FormuleTestBuilder.uneFormule;
import static com.example.salle_de_sport.fixtures.PeriodeTestBuilder.unePeriode;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RenouvelerDesAbonnementsTest {

  @InjectMocks
  private RenouvelerDesAbonnements renouvelerDesAbonnements;
  @Mock
  private AbonnementPersistence abonnementPersistence;

  @Test
  void executer_devrait_renvoyer_les_abonnements_renouveles_a_partir_de_la_date_fournie() {
    // Given
    String dateCible = "2022-12-31";
    Formule formule_6mois = uneFormule().avecNbrDeMois(6).build();
    Periode periode = unePeriode().avecDateDeDebut("2022-01-01").avecDateDeFin("2022-06-30").build();
    List<Periode> periodesAbonnement = new ArrayList<>(Arrays.asList(periode));

    Abonnement abonnementARenouveler =
            unAbonnement()
                .avecDateDeDebut("2022-01-01")
                .avecFormule(formule_6mois)
                .avecPeriodes(periodesAbonnement)
                .build();

    Abonnement abonnement = unAbonnement()
        .avecDateDeDebut("2022-01-01")
        .avecPeriodes(periodesAbonnement)
        .avecFormule(formule_6mois)
        .build();

    when(abonnementPersistence.recupererTousLesAbonements()).thenReturn(List.of(abonnement));
    when(abonnementPersistence.modifierDesAbonnements((List.of(abonnementARenouveler)))).thenReturn(List.of(abonnement));

    // When
    List<Abonnement> resultat = renouvelerDesAbonnements.executer(dateCible);

    // Then
    assertThat(resultat).size().isEqualTo(1);

    Abonnement abonnementRenouvele = resultat.get(0);
    assertThat(abonnementRenouvele.getId()).isEqualTo(abonnementARenouveler.getId());
    List<Periode> periodes = abonnementRenouvele.getPeriodes();

    assertThat(periodes.size()).isEqualTo(2);
    assertThat(periodes.get(1).getDateDeDebut()).isEqualTo("2022-07-01");
    assertThat(periodes.get(1).getDateDeFin()).isEqualTo("2022-12-31");
  }
}
