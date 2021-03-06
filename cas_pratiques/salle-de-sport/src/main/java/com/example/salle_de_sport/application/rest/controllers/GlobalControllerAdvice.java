package com.example.salle_de_sport.application.rest.controllers;

import com.example.salle_de_sport.domain.exceptions.AbonnementNonTrouveException;
import com.example.salle_de_sport.domain.exceptions.FormuleNonTrouveeException;
import com.example.salle_de_sport.domain.exceptions.SouscriptionAbonnementNonTrouveeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ResponseBody
    @ExceptionHandler(AbonnementNonTrouveException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String abonnementNonTrouve(AbonnementNonTrouveException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(FormuleNonTrouveeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String formuleNonTrouvee(FormuleNonTrouveeException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(SouscriptionAbonnementNonTrouveeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String souscriptionAbonnementNonTrouvee(SouscriptionAbonnementNonTrouveeException ex) {
        return ex.getMessage();
    }
}
