package com.example.salle_de_sport.infrastructure.api.formule_service;

import com.example.salle_de_sport.domain.models.Formule;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

class FormuleServicePersistanceWebClientTest extends MockServerTest {

	private FormuleServicePersistanceWebClient formuleServicePersistanceWebClient ;

	@BeforeEach
	void setUp() {
		String baseUrl = String.format("http://localhost:%s", mockServer.getPort());
		WebClient webClient = WebClient.builder().baseUrl(baseUrl).build();
		formuleServicePersistanceWebClient = new FormuleServicePersistanceWebClient(webClient);

	}

	@Test
	void recupererToutesLesFormules_devrait_renvoyer_la_liste_de_toutes_les_formules() throws Exception {

		// Given
		List<Formule> formulesRenvoyees = List.of(new Formule(1L, 20.0, 3));
		renvoyerReponse(200, formulesRenvoyees);

		// When
		List<Formule> formules = formuleServicePersistanceWebClient.recupererToutesLesFormules();

		// Then
		String urlRequeteAttendue = "/api/formules";
		RecordedRequest requeteEnregistree = mockServer.takeRequest(1, SECONDS);
		assertThat(requeteEnregistree.getPath()).isEqualTo(urlRequeteAttendue);
		assertThat(requeteEnregistree.getMethod()).isEqualTo("GET");
		assertThat(formules).hasSize(1);
		assertThat(formules.get(0).getId()).isEqualTo(1);
		assertThat(formules.get(0).getPrixDeBase()).isEqualTo(20);
		assertThat(formules.get(0).getNbrDeMois()).isEqualTo(3);
	}

	@Test
	void recupererUneFormules_devrait_renvoyer_une_formule_par_son_id() throws Exception {

		// Given
		Formule formuleEnvoyee = new Formule(2L, 10.0, 6);
		renvoyerReponse(200, formuleEnvoyee);
		// When
		Optional<Formule> formule = formuleServicePersistanceWebClient.recupererUneFormule(2L);

		// Then
		String urlRequeteAttendue = "/api/formules/2";
		RecordedRequest requeteEnregistree = mockServer.takeRequest(1, SECONDS);
		assertThat(requeteEnregistree.getPath()).isEqualTo(urlRequeteAttendue);
		assertThat(formule.get().getId()).isEqualTo(2);
		assertThat(formule.get().getPrixDeBase()).isEqualTo(10);
		assertThat(formule.get().getNbrDeMois()).isEqualTo(6);
	}

	@Test
	void creerUneFormule_devrait_renvoyer_la_formule_cree() throws Exception {

		// Given
		Formule formuleEnvoyee = new Formule(3L, 50.0, 2);
		renvoyerReponse(200, formuleEnvoyee);

		// When
		Formule formuleCree = formuleServicePersistanceWebClient.creerUneFormule(formuleEnvoyee);

		// Then
		assertThat(formuleCree.getId()).isEqualTo(3);
		assertThat(formuleCree.getPrixDeBase()).isEqualTo(50);
		assertThat(formuleCree.getNbrDeMois()).isEqualTo(2);
	}

	@Test
	void modifierUneFormule_devrait_modifier_une_formule() throws Exception {

		// Given
		Formule formuleModifiee = new Formule(3L, 10.0, 6);
		renvoyerReponse(200, formuleModifiee);

		// When
		Formule formule = formuleServicePersistanceWebClient.modifierUneFormule(formuleModifiee);

		// Then
		assertThat(formule.getId()).isEqualTo(3);
		assertThat(formule.getPrixDeBase()).isEqualTo(10);
		assertThat(formule.getNbrDeMois()).isEqualTo(6);
	}
}