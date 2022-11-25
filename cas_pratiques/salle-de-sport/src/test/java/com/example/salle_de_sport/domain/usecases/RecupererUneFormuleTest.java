package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Formule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.salle_de_sport.fixtures.FormuleTestBuilder.uneFormule;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecupererUneFormuleTest {

  @InjectMocks
  private RecupererUneFormule recupererUneFormule;
  @Mock
  private FormulePersistence formulePersistence;

  @Test
  void executer_devrait_renvoyer_la_formule_avec_l_id_fourni() {
    // Given
    Formule formule = uneFormule().build();
    when(formulePersistence.recupererUneFormule(formule.getId())).thenReturn(Optional.of(formule));

    // When
    Formule resultat = recupererUneFormule.executer(formule.getId());

    // Then
    assertThat(resultat).isInstanceOf(Formule.class);
    assertThat(resultat).isEqualTo(formule);
  }
}
