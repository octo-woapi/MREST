package com.example.salle_de_sport.domain.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static com.example.salle_de_sport.fixtures.PeriodeTestBuilder.unePeriode;

@ExtendWith(MockitoExtension.class)
class PeriodeTest {

    @Test
    void contient_devrait_renvoyer_true_si_la_date_fournie_se_trouve_dans_la_periode() {
        // Given
        String dateCible = "2022-03-01";
        Periode periode = unePeriode()
                .avecDateDeDebut("2022-01-01")
                .avecDateDeFin("2022-06-01")
                .build();

        // When
        boolean resultat = periode.contient(dateCible);

        // Then
        assertThat(resultat).isTrue();
    }
}
