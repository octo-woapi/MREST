package com.example.salle_de_sport.domain.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ReductionTest {

    @Test
    void calculer_devrait_renvoyer_une_reduction_de_20_pour_cent_pour_un_etudiant() {
        // Given
        boolean isEtudiant = true;
        int prixDeBase = 100;
        double prixReduitAttendu = 80.0;

        Reduction reduction = new Reduction(isEtudiant);

        // When
        Double resultat = reduction.calculer(prixDeBase);

        // Then
        assertThat(resultat).isEqualTo(prixReduitAttendu);
    }

    @Test
    void calculer_devrait_renvoyer_un_prix_sans_reduction_pour_un_non_etudiant() {
        // Given
        boolean isEtudiant = false;
        int prixDeBase = 100;
        double prixReduitAttendu = 100.0;

        Reduction reduction = new Reduction(isEtudiant);

        // When
        Double resultat = reduction.calculer(prixDeBase);

        // Then
        assertThat(resultat).isEqualTo(prixReduitAttendu);
    }
}
