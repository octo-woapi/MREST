package com.example.salle_de_sport.infrastructure.mailer;

import com.example.salle_de_sport.domain.usecases.MessageriePersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MessagerieMailer implements MessageriePersistence {

  private static final Logger LOGGER = LoggerFactory.getLogger(MessagerieMailer.class);

  @Override
  public void envoyerUnEmail(String email, String message) {
    LOGGER.info("Envoie d'un e-mail à " + email);
    LOGGER.info("Contenu : " + message);
  }
}
