package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Formule;
import com.example.salle_de_sport.infrastructure.database.DatabaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.example.salle_de_sport.fixtures.FormuleTestBuilder.uneFormule;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecupererToutesLesFormulesTest {

  @InjectMocks
  private RecupererToutesLesFormules recupererToutesLesFormules;
  @Mock
  private FormulePersistence formulePersistence;

  @Test
  void executer_devrait_renvoyer_toutes_les_formules_savegardees() {
    // Given
    Formule premiereFormule = uneFormule().avecPrixDeBase(250.0).build();
    Formule deuxiemeFormule =uneFormule().avecPrixDeBase(80.0).build();
    List<Formule> fomules = List.of(premiereFormule, deuxiemeFormule);

    when(formulePersistence.recupererToutesLesFormules()).thenReturn(fomules);

    // When
    List<Formule> resultat = recupererToutesLesFormules.executer();

    // Then
    assertThat(resultat).isEqualTo(fomules);
  }
}
