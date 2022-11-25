package com.example.salle_de_sport.infrastructure.database.configuration;

import com.example.salle_de_sport.domain.models.Abonnement;
import com.example.salle_de_sport.domain.models.Formule;
import com.example.salle_de_sport.domain.models.SouscriptionAbonnement;
import com.example.salle_de_sport.domain.usecases.CreerUnAbonnement;
import com.example.salle_de_sport.domain.usecases.CreerUneFormule;
import com.example.salle_de_sport.domain.usecases.EnvoyerUnEmailDeSouscription;
import com.example.salle_de_sport.domain.usecases.RecupererToutesLesFormules;
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
      CreerUneFormule creerUneFormule,
      RecupererToutesLesFormules recupererToutesLesFormules,
      CreerUnAbonnement creerUnAbonnement,
      EnvoyerUnEmailDeSouscription envoyerUnEmailDeSouscription) {

    AtomicInteger compteur = new AtomicInteger(1);

    return args -> {
      creerUneFormule.executer(new Formule(50.0, 6));
      creerUneFormule.executer(new Formule(100.0, 12));

      recupererToutesLesFormules
          .executer()
          .forEach(
              formule -> {
                LOGGER.info("Création de la formule :" + formule);

                String email = String.format("user_%s@example.net", compteur.getAndAdd(1));
                Abonnement abonnement =
                    creerUnAbonnement.executer(new Abonnement(email, true, "2022-01-01", formule));
                LOGGER.info("Création de l'abonnement : " + abonnement);
//
//                SouscriptionAbonnement souscriptionAbonnement =
//                    envoyerUnEmailDeSouscription.executer(abonnement.getId(), email);
//                LOGGER.info("Création de la souscription :" + souscriptionAbonnement);
              });
    };
  }
}
