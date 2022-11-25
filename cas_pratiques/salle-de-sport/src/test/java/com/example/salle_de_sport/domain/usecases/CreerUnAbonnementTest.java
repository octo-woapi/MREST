package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Abonnement;
import com.example.salle_de_sport.domain.models.Formule;
import com.example.salle_de_sport.domain.models.Periode;
import com.example.salle_de_sport.infrastructure.database.DatabaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static com.example.salle_de_sport.fixtures.AbonnementTestBuilder.unAbonnement;
import static com.example.salle_de_sport.fixtures.FormuleTestBuilder.uneFormule;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreerUnAbonnementTest {

  @InjectMocks
  private CreerUnAbonnement creerUnAbonnement;
  @Mock
  private  FormulePersistence formulePersistence;
  @Mock
  private  AbonnementPersistence abonnementPersistence;

  @Test
  void executer_devrait_renvoyer_l_abonnement_cree_et_sauvgarde() {
    // Given
    Formule formule = uneFormule().build();
    List<Periode> periodes = List.of(new Periode("2022-01-01", formule.getNbrDeMois()));
    Abonnement abonnement = unAbonnement().avecId(null).avecFormule(formule).avecPeriodes(periodes).build();
    when(formulePersistence.recupererUneFormule(formule.getId())).thenReturn(Optional.of(formule));
    when(abonnementPersistence.creerUnAbonnement(abonnement)).thenReturn(abonnement);

    // When
    Abonnement resultat = creerUnAbonnement.executer(abonnement);

    // Then
    assertThat(resultat).isInstanceOf(Abonnement.class);
    assertThat(resultat.getId()).isEqualTo(abonnement.getId());
    assertThat(resultat.getFormuleChoisie()).isEqualTo(abonnement.getFormuleChoisie());
    assertThat(resultat.getDateDeDebut()).isEqualTo(abonnement.getDateDeDebut());

  }
}
