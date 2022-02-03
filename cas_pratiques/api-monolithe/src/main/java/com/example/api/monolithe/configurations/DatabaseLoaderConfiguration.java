package com.example.api.monolithe.configurations;

import com.example.api.monolithe.entities.Abonnement;
import com.example.api.monolithe.entities.Formule;
import com.example.api.monolithe.entities.SouscriptionAbonnement;
import com.example.api.monolithe.repositories.AbonnementRepository;
import com.example.api.monolithe.repositories.FormuleRepository;
import com.example.api.monolithe.repositories.SouscriptionAbonnementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class DatabaseLoaderConfiguration {

  private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseLoaderConfiguration.class);

  @Bean
  CommandLineRunner initDatabase(
      FormuleRepository formuleRepository,
      AbonnementRepository abonnementRepository,
      SouscriptionAbonnementRepository souscriptionAbonnementRepository) {
    AtomicInteger compteur = new AtomicInteger(1);

    return args -> {
      formuleRepository.save(new Formule(50, 6));
      formuleRepository.save(new Formule(100, 12));

      formuleRepository
          .findAll()
          .forEach(
              formule -> {
                LOGGER.info("Preloaded " + formule);

                String email = String.format("user_%s@example.net", compteur.getAndAdd(1));
                Abonnement abonnement =
                    abonnementRepository.save(new Abonnement(email, true, "2022-01-01", formule));
                LOGGER.info("Preloaded " + abonnement);

                SouscriptionAbonnement souscriptionAbonnement =
                    souscriptionAbonnementRepository.save(
                        new SouscriptionAbonnement(abonnement.getEmail(), abonnement));
                LOGGER.info("Preloaded " + souscriptionAbonnement);
              });
    };
  }
}
