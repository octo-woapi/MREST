package com.exemple.formule_service_kafka.domain.models;

import static com.exemple.formule_service_kafka.fixures.FormuleTestBuilder.uneFormule;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FormuleUTest {

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
