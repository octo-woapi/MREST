package com.example.salle_de_sport.infrastructure.api.formule_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
@Configuration
public class FomuleWebClient {

		@Bean
		public WebClient webClient(
			@Value("${formule.baseUrl}") String fomuleBaseUrl, WebClient.Builder builder) {
			return builder.baseUrl(fomuleBaseUrl).build();
		}
}
