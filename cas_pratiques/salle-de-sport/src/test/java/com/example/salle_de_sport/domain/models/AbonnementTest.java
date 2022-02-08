package com.example.salle_de_sport.domain.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.example.salle_de_sport.fixtures.AbonnementTestBuilder.unAbonnement;
import static com.example.salle_de_sport.fixtures.PeriodeTestBuilder.unePeriode;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AbonnementTest {

    @Test
    void seraFiniALaDateDonnee_devrait_retourner_true_si_l_abonnement_sera_fini_a_la_date_fournie() {
        // Given
        String dateDeDebut = "2022-01-01";
        String dateDeFin = "2022-05-31";
        String dateCible = "2022-12-01";
        Periode periode = unePeriode()
                .avecDateDeDebut(dateDeDebut)
                .avecDateDeFin(dateDeFin)
                .build();

        Abonnement abonnement = unAbonnement().avecPeriodes(List.of(periode)).build();

        // When
        boolean resultat = abonnement.seraFiniALaDateDonnee(dateCible);

        // Then
        assertThat(resultat).isTrue();
    }

    @Test
    void estEnCours_devrait_retourner_true_si_l_abonnement_est_en_cours_selon_une_date_fournie() {
        // Given
        String dateDeDebut = "2022-01-01";
        String dateDeFin = "2022-05-31";
        String dateCible = "2022-03-01";

        Periode periode = unePeriode()
                .avecDateDeDebut(dateDeDebut)
                .avecDateDeFin(dateDeFin)
                .build();

        Abonnement abonnement = unAbonnement().avecPeriodes(List.of(periode)).build();

        // When
        boolean resultat = abonnement.estEnCours(dateCible);

        // Then
        assertThat(resultat).isTrue();
    }

    @Test
    void renouveler_devrait_renvoyer_l_abonnement_avec_une_nouvelle_periode_d_abonnement() {
        // Given
        String dateDeDebut = "2022-01-01";
        String dateDeFin = "2022-06-30";
        Periode periode = unePeriode()
                .avecDateDeDebut(dateDeDebut)
                .avecDateDeFin(dateDeFin)
                .build();
        List<Periode> periodesInitiales = new ArrayList<>();
        periodesInitiales.add(periode);

        List<Periode> periodesAttendues = List.of(
                periode,
                unePeriode()
                        .avecId(null)
                        .avecDateDeDebut("2022-07-01")
                        .avecDateDeFin("2022-12-31")
                        .build());

        Abonnement abonnement = unAbonnement()
                .avecPeriodes(periodesInitiales)
                .build();

        // When
        Abonnement resultat = abonnement.renouveler();

        // Then
        assertThat(resultat.getPeriodes()).isEqualTo(periodesAttendues);
    }
}
