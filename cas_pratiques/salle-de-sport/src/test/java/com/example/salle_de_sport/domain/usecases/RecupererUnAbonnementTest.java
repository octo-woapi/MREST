package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Abonnement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.salle_de_sport.fixtures.AbonnementTestBuilder.unAbonnement;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecupererUnAbonnementTest {

  @InjectMocks
  private RecupererUnAbonnement recupererUnAbonnement;
  @Mock
  private AbonnementPersistence abonnementPersistence;

  @Test
  void executer_devrait_renvoyer_l_abonnement_avec_l_id_fourni() {
    // Given
    Abonnement abonnement = unAbonnement().build();
       when(abonnementPersistence.recupererUnAbonnement(1L)).thenReturn(Optional.of(abonnement));

    // When
    Abonnement resultat = recupererUnAbonnement.executer(abonnement.getId());

    // Then
    assertThat(resultat).isInstanceOf(Abonnement.class);
    assertThat(resultat).isEqualTo(abonnement);
  }
}
