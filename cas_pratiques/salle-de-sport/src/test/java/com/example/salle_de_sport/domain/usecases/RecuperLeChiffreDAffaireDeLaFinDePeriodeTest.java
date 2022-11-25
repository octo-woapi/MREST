package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Formule;
import com.example.salle_de_sport.infrastructure.database.DatabaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.example.salle_de_sport.fixtures.AbonnementTestBuilder.unAbonnement;
import static com.example.salle_de_sport.fixtures.FormuleTestBuilder.uneFormule;
import static org.assertj.core.api.Assertions.assertThat;

class RecuperLeChiffreDAffaireDeLaFinDePeriodeTest extends DatabaseRepositoryTest {

  @Autowired private CreerUnAbonnement creerUnAbonnement;

  @Autowired
  private RecuperLeChiffreDAffaireDeLaFinDePeriode recuperLeChiffreDAffaireDeLaFinDePeriode;

  @Test
  void executer_devrait_renvoyer_le_chiffre_d_affaire_des_periodes_en_cours_a_la_date_fournie() {
    // Given
    String dateCible = "2022-07-01";
    double chiffreDAffaireAttendu = 150;

    Formule formule_150 =
        creerUneFormule(
            uneFormule().avecId(null).avecNbrDeMois(12).avecPrixDeBase(150.0).build());
    Formule formule_100 =
        creerUneFormule(
            uneFormule().avecId(null).avecNbrDeMois(6).avecPrixDeBase(100.0).build());

    creerUnAbonnement.executer(
        unAbonnement().avecId(null).avecFormule(formule_150).avecDateDeDebut("2022-01-01").build());
    creerUnAbonnement.executer(
        unAbonnement().avecId(null).avecFormule(formule_100).avecDateDeDebut("2022-01-01").build());

    // When
    double resultat = recuperLeChiffreDAffaireDeLaFinDePeriode.executer(dateCible);

    // Then
    assertThat(resultat).isEqualTo(chiffreDAffaireAttendu);
  }
}
