package com.example.formule_service.infrastructure.database.configuration;

import com.example.formule_service.domain.models.Formule;
import com.example.formule_service.domain.usecases.CreerUneFormule;
import com.example.formule_service.domain.usecases.RecupererToutesLesFormules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseLoaderConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseLoaderConfiguration.class);

	@Bean
	CommandLineRunner initDatabase(
		CreerUneFormule creerUneFormule,
		RecupererToutesLesFormules recupererToutesLesFormules) {

		return args -> {
			creerUneFormule.executer(new Formule(50.0, 6));
			creerUneFormule.executer(new Formule(100.0, 12));

			recupererToutesLesFormules
				.executer()
				.forEach(
					formule -> {
						LOGGER.info("Création de la formule :" + formule);
					});
		};
	}
}
