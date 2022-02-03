package com.example.api.monolithe.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FakeMailer implements Mailer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FakeMailer.class);

    @Override
    public void envoyerEmail(String email, String message) {
        LOGGER.info("Envoie d'un e-mail à " + email);
        LOGGER.info("Contenu : " + message);
    }
}
