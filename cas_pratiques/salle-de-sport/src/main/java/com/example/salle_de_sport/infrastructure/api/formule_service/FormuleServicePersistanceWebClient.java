package com.example.salle_de_sport.infrastructure.api.formule_service;
import com.example.salle_de_sport.domain.models.Formule;
import com.example.salle_de_sport.domain.usecases.FormulePersistence;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class FormuleServicePersistanceWebClient implements FormulePersistence {

	private static final String FORMULE_SERVICE_API_GET_FORMULE ="api/formules";

	private final WebClient webClient;

	@Override
	public List<Formule> recupererToutesLesFormules() {
		return webClient
			.get()
			.uri(FORMULE_SERVICE_API_GET_FORMULE)
			.retrieve()
			.toEntityList(FormuleApi.class)
			.block()
			.getBody()
			.stream()
			.map(FormuleApi::convertirEnFormule)
			.toList();
	}

	@Override
	public Optional<Formule> recupererUneFormule(Long id) {

	return Optional.of(FormuleApi.convertirEnFormule( webClient
			.get()
			.uri(FORMULE_SERVICE_API_GET_FORMULE + "/" + id)
			.retrieve()
			.bodyToMono(FormuleApi.class)
			.block()));

	}

	@Override
	public Formule creerUneFormule(Formule formule) {
		FormuleApi formuleApi = webClient
			.post()
			.uri(FORMULE_SERVICE_API_GET_FORMULE)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.bodyValue(formule)
			.retrieve()
			.bodyToMono(FormuleApi.class)
			.block();
		return FormuleApi.convertirEnFormule(formuleApi);
	}

	@Override
	public Formule modifierUneFormule(Formule formule) {
		Long idFormule = formule.getId();
		Double nouveauxPrix = formule.getPrixDeBase();
		FormuleApi formuleApi = webClient
			.put()
			.uri(FORMULE_SERVICE_API_GET_FORMULE + "/" + idFormule + "/prix")
			.bodyValue(nouveauxPrix)
			.retrieve()
			.bodyToMono(FormuleApi.class)
			.block();
		return FormuleApi.convertirEnFormule(formuleApi);
	}
}
