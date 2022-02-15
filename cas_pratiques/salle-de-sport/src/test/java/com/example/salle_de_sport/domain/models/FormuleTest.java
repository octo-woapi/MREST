package com.example.salle_de_sport.domain.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.salle_de_sport.fixtures.FormuleTestBuilder.uneFormule;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class FormuleTest {

    @Test
    void getDescription_devrait_renvoyer_la_descrition_de_la_formule() {
        // Given
        Formule formule = uneFormule().avecNbrDeMois(6).avecPrixDeBase(100.0).build();
        String descriptionAttendue = "Formule 6 mois à 100.0 euros";

        // When
        String resultat = formule.getDescription();

        // Then
        assertThat(resultat).isEqualTo(descriptionAttendue);
    }
}
