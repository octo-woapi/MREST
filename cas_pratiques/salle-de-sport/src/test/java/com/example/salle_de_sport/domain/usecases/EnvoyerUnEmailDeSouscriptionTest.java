package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Abonnement;
import com.example.salle_de_sport.domain.models.Formule;
import com.example.salle_de_sport.domain.models.SouscriptionAbonnement;
import com.example.salle_de_sport.infrastructure.database.DatabaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.example.salle_de_sport.fixtures.AbonnementTestBuilder.unAbonnement;
import static com.example.salle_de_sport.fixtures.FormuleTestBuilder.uneFormule;
import static com.example.salle_de_sport.fixtures.SouscriptionAbonnementTestBuilder.uneSouscriptionAbonnement;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnvoyerUnEmailDeSouscriptionTest {

  @InjectMocks
  private EnvoyerUnEmailDeSouscription envoyerUnEmailDeSouscription;
  @Mock
  private  AbonnementPersistence abonnementPersistence;
  @Mock
  private  SouscriptionAbonnementPersistence souscriptionAbonnementPersistence;
  @Mock
  private  MessageriePersistence messageriePersistence;


  @Test
  void executer_devrait_renvoyer_la_souscription_cree_et_sauvegardee() {
    // Given
    String email = "user+souscription@example.net";
    Formule formule = uneFormule().avecId(null).build();
    Abonnement abonnement = unAbonnement().avecFormule(formule).build();
    SouscriptionAbonnement  souscriptionAbonnement = uneSouscriptionAbonnement().avecAbonnement(abonnement).avecEmailDeSouscription(email).build();

    when(abonnementPersistence.recupererUnAbonnement(abonnement.getId())).thenReturn(Optional.of(abonnement));
    when(souscriptionAbonnementPersistence.creerUneSouscriptionAbonnement(email, abonnement)).thenReturn(souscriptionAbonnement);

    // When
    SouscriptionAbonnement resultat =
        envoyerUnEmailDeSouscription.executer(abonnement.getId(), email);

    // Then
    verify(messageriePersistence).envoyerUnEmail(email, "Bienvenu(e) chez CraftGym, profite bien de ton abonnement %s."
        .formatted(abonnement.getFormuleChoisie().getDescription()));

    assertThat(resultat).isInstanceOf(SouscriptionAbonnement.class);
    assertThat(resultat.getAbonnement()).isEqualTo(abonnement);
    assertThat(resultat.getEmail()).isEqualTo(email);
  }
}
