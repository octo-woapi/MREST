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
class ModifierLePrixDuneFormuleTest {

  @InjectMocks
  private ModifierLePrixDuneFormule modifierLePrixDuneFormule;
  @Mock
  FormulePersistence formulePersistence;

  @Test
  void executer_devrait_renvoyer_la_formule_sauvegardee_avec_le_prix_modifie() {
    // Given
    double nouveauPrix = 100.0;
    Formule formule = uneFormule().avecPrixDeBase(50.0).build();
    Formule nouvelleFormule = uneFormule().avecPrixDeBase(nouveauPrix).build();
    when(formulePersistence.recupererUneFormule(formule.getId())).thenReturn(Optional.of(formule));
    formule.setPrixDeBase(nouveauPrix);
    when(formulePersistence.modifierUneFormule(formule)).thenReturn(nouvelleFormule);


    // When
    Formule resultat = modifierLePrixDuneFormule.executer(formule.getId(), nouveauPrix);

    // Then
    assertThat(resultat).isInstanceOf(Formule.class);
    assertThat(resultat.getId()).isEqualTo(formule.getId());
    assertThat(resultat.getPrixDeBase()).isEqualTo(nouveauPrix);
  }
}
