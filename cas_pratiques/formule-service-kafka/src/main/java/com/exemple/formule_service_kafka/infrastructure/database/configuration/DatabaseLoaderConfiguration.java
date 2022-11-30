package com.exemple.formule_service_kafka.infrastructure.database.configuration;

import com.exemple.formule_service_kafka.domain.models.Formule;
import com.exemple.formule_service_kafka.domain.usecases.CreerUneFormule;
import com.exemple.formule_service_kafka.domain.usecases.RecupererToutesLesFormules;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
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
      CreerUneFormule creerUneFormule, RecupererToutesLesFormules recupererToutesLesFormules) {

    AtomicInteger compteur = new AtomicInteger(1);

    return args -> {
      creerUneFormule.executer(new Formule(UUID.randomUUID(), 50.0, 6));
      creerUneFormule.executer(new Formule(UUID.randomUUID(), 100.0, 12));

      recupererToutesLesFormules
          .executer()
          .forEach(
              formule -> {
                LOGGER.info("Création de la formule :" + formule);
              });
    };
  }
}
