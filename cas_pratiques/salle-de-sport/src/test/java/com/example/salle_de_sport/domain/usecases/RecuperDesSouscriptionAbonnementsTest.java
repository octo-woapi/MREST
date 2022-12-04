package com.example.salle_de_sport.domain.usecases;

import com.example.salle_de_sport.domain.models.Abonnement;
import com.example.salle_de_sport.domain.models.Formule;
import com.example.salle_de_sport.domain.models.SouscriptionAbonnement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.example.salle_de_sport.fixtures.AbonnementTestBuilder.unAbonnement;
import static com.example.salle_de_sport.fixtures.FormuleTestBuilder.uneFormule;
import static com.example.salle_de_sport.fixtures.SouscriptionAbonnementTestBuilder.uneSouscriptionAbonnement;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecuperDesSouscriptionAbonnementsTest {

	@InjectMocks
	private RecuperDesSouscriptionAbonnements recuperDesSouscriptionAbonnements;

	@Mock
	private SouscriptionAbonnementPersistence souscriptionAbonnementPersistence;

	@Test
	void executer_devrait_renvoyer_les_souscriptions_liees_a_un_abonnement() {
		// Given
		String email = "user+souscription@example.net";
		Formule formule = uneFormule().build();
		Abonnement abonnement = unAbonnement().avecFormule(formule).build();
		SouscriptionAbonnement souscriptionAbonnement = uneSouscriptionAbonnement().avecAbonnement(abonnement).avecEmailDeSouscription(email).build();

		when(souscriptionAbonnementPersistence.recuperDesSouscriptionAbonnementsParId(abonnement.getId())).thenReturn(List.of(souscriptionAbonnement));

		// When
		List<SouscriptionAbonnement> resultat =
			recuperDesSouscriptionAbonnements.executer(abonnement.getId());

		// Then
		assertThat(resultat).isEqualTo(List.of(souscriptionAbonnement));
	}
}
