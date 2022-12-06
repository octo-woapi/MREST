package com.example.formule_service.application.rest.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class FormuleApi {

    private Long id;
    private Double prixDeBase;
    private int nbrDeMois;

}
