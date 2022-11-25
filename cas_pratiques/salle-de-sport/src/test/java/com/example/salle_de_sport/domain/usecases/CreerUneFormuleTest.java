package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Formule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.example.salle_de_sport.fixtures.FormuleTestBuilder.uneFormule;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreerUneFormuleTest {

  @InjectMocks
  private CreerUneFormule creerUneFormule;
  @Mock
  private FormulePersistence formulePersistence;

  @Test
  void executer_devrait_renvoyer_la_formule_creee_et_savegardee() {
    // Given
    Formule formuleAttendue = uneFormule().build();
    when(formulePersistence.creerUneFormule(formuleAttendue)).thenReturn(formuleAttendue);


    // When
    Formule resultat = creerUneFormule.executer(formuleAttendue);

    // Then
    assertThat(resultat).isInstanceOf(Formule.class);
    assertThat(resultat.getId()).isEqualTo(formuleAttendue.getId());
    assertThat(resultat.getPrixDeBase()).isEqualTo(formuleAttendue.getPrixDeBase());
    assertThat(resultat.getNbrDeMois()).isEqualTo(formuleAttendue.getNbrDeMois());
  }
}
