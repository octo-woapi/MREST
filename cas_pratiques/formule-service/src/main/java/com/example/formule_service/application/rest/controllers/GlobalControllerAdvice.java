package com.example.formule_service.application.rest.controllers;

import com.example.formule_service.domain.exceptions.FormuleNonTrouveeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ResponseBody
    @ExceptionHandler(FormuleNonTrouveeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String formuleNonTrouvee(FormuleNonTrouveeException ex) {
        return ex.getMessage();
    }

}
