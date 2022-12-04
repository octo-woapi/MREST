package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Abonnement;
import com.example.salle_de_sport.domain.models.Formule;
import com.example.salle_de_sport.domain.models.Periode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.example.salle_de_sport.fixtures.AbonnementTestBuilder.unAbonnement;
import static com.example.salle_de_sport.fixtures.FormuleTestBuilder.uneFormule;
import static com.example.salle_de_sport.fixtures.PeriodeTestBuilder.unePeriode;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecuperLeChiffreDAffaireDeLaFinDePeriodeTest {

  @InjectMocks
  private RecuperLeChiffreDAffaireDeLaFinDePeriode recuperLeChiffreDAffaireDeLaFinDePeriode;
  @Mock
  private AbonnementPersistence abonnementPersistence;

  @Test
  void executer_devrait_renvoyer_le_chiffre_d_affaire_des_periodes_en_cours_a_la_date_fournie() {
    // Given
    String dateCible = "2022-07-01";
    double chiffreDAffaireAttendu = 160;
    Periode periode = unePeriode().build();

    Formule formule_150 = uneFormule().avecNbrDeMois(12).avecPrixDeBase(150.0).build();
    Formule formule_100 = uneFormule().avecNbrDeMois(6).avecPrixDeBase(100.0).build();
    Abonnement premiereAbonnement = unAbonnement().avecPeriodes(List.of(periode)).avecFormule(formule_150).avecDateDeDebut("2022-01-01").build();
    Abonnement deuxiemeAbonnement = unAbonnement().avecPeriodes(List.of(periode)).avecFormule(formule_100).avecDateDeDebut("2022-01-01").build();

    when(abonnementPersistence.recupererTousLesAbonements()).thenReturn(List.of(premiereAbonnement, deuxiemeAbonnement));

    // When
    double resultat = recuperLeChiffreDAffaireDeLaFinDePeriode.executer(dateCible);

    // Then
    assertThat(resultat).isEqualTo(chiffreDAffaireAttendu);
  }
}
