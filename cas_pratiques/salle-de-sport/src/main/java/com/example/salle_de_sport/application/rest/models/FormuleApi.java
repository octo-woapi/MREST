package com.example.salle_de_sport.application.rest.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class FormuleApi {

    private Long id;
    private Double prixDeBase;
    private int nbrDeMois;

}
