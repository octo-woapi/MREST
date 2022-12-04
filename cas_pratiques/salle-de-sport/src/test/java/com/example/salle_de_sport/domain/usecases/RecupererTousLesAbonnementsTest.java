package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Abonnement;
import com.example.salle_de_sport.domain.models.Formule;
import com.example.salle_de_sport.infrastructure.database.DatabaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.example.salle_de_sport.fixtures.AbonnementTestBuilder.unAbonnement;
import static com.example.salle_de_sport.fixtures.FormuleTestBuilder.uneFormule;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecupererTousLesAbonnementsTest {

  @InjectMocks
  private RecupererTousLesAbonnements recupererTousLesAbonnements;
  @Mock
  AbonnementPersistence abonnementPersistence;

  @Test
  void executer_devrait_renvoyer_tous_les_abonnements_savegardees() {
    // Given
    Formule formule = uneFormule().build();
    Abonnement premierAbonnement = unAbonnement().avecFormule(formule).build();
    Abonnement deuxiemeAbonnement = unAbonnement().avecFormule(formule).build();
    List<Abonnement> abonnements = List.of(premierAbonnement, deuxiemeAbonnement);
    when(abonnementPersistence.recupererTousLesAbonements()).thenReturn(abonnements);

    // When
    List<Abonnement> resultat = recupererTousLesAbonnements.executer();

    // Then
    assertThat(resultat).isEqualTo(List.of(premierAbonnement, deuxiemeAbonnement));
  }
}
