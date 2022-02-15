package com.example.salle_de_sport.domain.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DatePersonnaliseeTest {

  @Test
  void estAvant() {
    // Given
    DatePersonnalisee datePersonnalisee = new DatePersonnalisee("2022-05-01");
    DatePersonnalisee dateCible = new DatePersonnalisee("2022-10-01");

    // When
    boolean resultat = datePersonnalisee.estAvant(dateCible);

    // Then
    assertThat(resultat).isTrue();
  }

  @Test
  void estApres_devrait_renvoyer_true_si_la_date_personnalisee_est_posterieure_a_une_date_cible() {
    // Given
    DatePersonnalisee datePersonnalisee = new DatePersonnalisee("2022-05-01");
    DatePersonnalisee dateCible = new DatePersonnalisee("2022-01-01");

    // When
    boolean resultat = datePersonnalisee.estApres(dateCible);

    // Then
    assertThat(resultat).isTrue();
  }

  @Test
  void estEgale_devrait_renvoyer_true_si_la_date_personnalisee_est_egale_a_une_date_cible() {
    // Given
    DatePersonnalisee datePersonnalisee = new DatePersonnalisee("2022-05-01");
    DatePersonnalisee dateCible = new DatePersonnalisee("2022-05-01");

    // When
    boolean resultat = datePersonnalisee.estEgale(dateCible);

    // Then
    assertThat(resultat).isTrue();
  }

  @Test
  void
      estApresOuEgale_devrait_renvoyer_true_si_la_date_personnalisee_est_identique_ou_posterieure_a_une_date_cible() {
    // Given
    DatePersonnalisee datePersonnalisee = new DatePersonnalisee("2022-05-01");

    DatePersonnalisee dateCibleAnterieure = new DatePersonnalisee("2022-01-01");
    DatePersonnalisee dateCibleIdentique = new DatePersonnalisee("2022-05-01");

    // When
    boolean resultat1 = datePersonnalisee.estApresOuEgale(dateCibleAnterieure);
    boolean resultat2 = datePersonnalisee.estApresOuEgale(dateCibleIdentique);

    // Then
    assertThat(resultat1).isTrue();
    assertThat(resultat2).isTrue();
  }

  @Test
  void ajouterDesMois_devrait_renvoyer_la_date_personnalisee_avec_les_mois_supplementaires() {
    // Given
    int nbrDeMois = 5;
    DatePersonnalisee datePersonnalisee = new DatePersonnalisee("2022-05-05");

    DatePersonnalisee datePersonnaliseeAttendue = new DatePersonnalisee("2022-10-05");

    // When
    DatePersonnalisee resultat = datePersonnalisee.ajouterDesMois(nbrDeMois);

    // Then
    assertThat(resultat).isEqualTo(datePersonnaliseeAttendue);
  }

  @Test
  void unJourAvant_devrait_renvoyer_la_date_personnalisee_avec_le_jour_precedent() {
    // Given
    DatePersonnalisee datePersonnalisee = new DatePersonnalisee("2022-05-10");

    DatePersonnalisee datePersonnaliseeAttendue = new DatePersonnalisee("2022-05-09");

    // When
    DatePersonnalisee resultat = datePersonnalisee.unJourAvant();

    // Then
    assertThat(resultat).isEqualTo(datePersonnaliseeAttendue);
  }

  @Test
  void unJourApres_devrait_renvoyer_la_date_personnalisee_avec_le_jour_suivant() {
    // Given
    DatePersonnalisee datePersonnalisee = new DatePersonnalisee("2022-02-28");

    DatePersonnalisee datePersonnaliseeAttendue = new DatePersonnalisee("2022-03-01");

    // When
    DatePersonnalisee resultat = datePersonnalisee.unJourApres();

    // Then
    assertThat(resultat).isEqualTo(datePersonnaliseeAttendue);
  }
}
